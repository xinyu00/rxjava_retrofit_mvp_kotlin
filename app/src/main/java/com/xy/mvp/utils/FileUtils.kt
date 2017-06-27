package com.xy.mvp.utils

import android.os.Environment
import android.text.TextUtils

import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 * anthor:Created by tianchen on 2017/5/17.
 * email:963181974@qq.com
 * 文件工具类
 */

object FileUtils {
    private val TAG = "FileUtils"
    private val DEFAULT_ENCODE = "UTF-8"
    private val appPath = "/xyapp/"

    /**
     * 获取App文件目录
     */
    val appFilePath: String
        get() = innerSDCardPath!! + appPath

    /**
     * 获取App文件目录
     */
    val appFile: File
        get() = File(innerSDCardPath!! + appPath)

    /**
     * 创建App文件目录
     */
    fun createAppFile() {
        val path = innerSDCardPath
        createDirectory(path!! + appPath)
        createDirectory(path + appPath + "log/")
        createDirectory(path + appPath + "image/")
    }

    /**
     * 判断内置sd卡是否可用

     * @return true 能 false 不能
     */
    private val sDcardIsExist: Boolean
        get() = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED

    /**
     * 获取内置SD卡路径
     */
    private val innerSDCardPath: String?
        get() {
            if (sDcardIsExist) {
                return Environment.getExternalStorageDirectory().path
            } else {
                LogUtils.e(TAG, "无法获取内置sd卡")
                return null
            }
        }

    /**
     * 获取内置SD卡路径
     */
    val innerSDCardFile: File?
        get() {
            if (sDcardIsExist) {
                return Environment.getExternalStorageDirectory()
            } else {
                LogUtils.e(TAG, "无法获取内置sd卡")
                return null
            }
        }

    /**
     * 获取文件路径
     */
    val filePath: File?
        get() {
            if (sDcardIsExist) {
                return Environment.getExternalStorageDirectory()
            } else {
                LogUtils.e(TAG, "无法获取内置sd卡")
                return null
            }
        }

    /**
     * 创建目录
     */

    fun createDirectory(path: String) {
        if (TextUtils.isEmpty(path)) {
            return
        }
        try {
            // 获得文件对象
            val f = File(path)
            if (!f.exists()) {
                // 如果路径不存在,则创建
                val flag = f.mkdirs()
                LogUtils.e(TAG, "createDirectory中mkdirs方法执行:" + flag)
            }
        } catch (e: Exception) {
            LogUtils.e(TAG, "创建目录错误.path=" + path + "\n" + e.toString())
            e.printStackTrace()
        }

    }

    /**
     * 新建文件.

     * @param path 文件路径
     */
    fun createFile(path: String) {
        if (TextUtils.isEmpty(path)) {
            return
        }
        try {
            // 获得文件对象
            val f = File(path)
            if (f.exists()) {
                return
            }
            // 如果路径不存在,则创建
            if (!f.parentFile.exists()) {
                val flag = f.parentFile.mkdirs()
                LogUtils.e(TAG, "createFile中mkdirs方法执行:" + flag)
            }
            val flag = f.createNewFile()
            LogUtils.e(TAG, "createFile中createNewFile方法执行:" + flag)
        } catch (e: Exception) {
            LogUtils.e(TAG, "创建文件错误.path=" + path + "\n" + e.toString())
            e.printStackTrace()
        }

    }

    /**
     * 保存文件(文件不存在会自动创建).

     * @param path     文件路径
     * *
     * @param content  文件内容
     * *
     * @param encoding 编码(UTF-8/gb2312/...)
     */
    @JvmOverloads fun saveFile(path: String, content: String, encoding: String = DEFAULT_ENCODE) {
        var encoding = encoding
        var fileOutputStream: FileOutputStream? = null
        var bw: BufferedOutputStream? = null
        try {
            // 获得文件对象
            val f = File(path)
            // 默认编码UTF-8
            encoding = if (TextUtils.isEmpty(encoding)) DEFAULT_ENCODE else encoding
            // 如果路径不存在,则创建
            if (!f.parentFile.exists()) {
                val flag = f.parentFile.mkdirs()
                LogUtils.e(TAG, "mkdirs方法执行" + flag)
            }
            // 开始保存文件
            fileOutputStream = FileOutputStream(path)
            bw = BufferedOutputStream(fileOutputStream)
            bw.write(content.toByteArray(charset(encoding)))
        } catch (e: Exception) {
            LogUtils.e(TAG, "保存文件错误.path=" + path + ",content=" + content + ",encoding=" + "\n" + e.toString())
            e.printStackTrace()
        } finally {
            if (bw != null) {
                try {
                    bw.close()
                } catch (e1: IOException) {
                    e1.printStackTrace()
                }

            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close()
                } catch (e1: IOException) {
                    e1.printStackTrace()
                }

            }
        }
    }
}
/**
 * 保存文件(文件不存在会自动创建).

 * @param path    文件路径
 * *
 * @param content 文件内容
 */
