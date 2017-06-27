package com.xy.mvp.utils

import android.util.Log

import com.xy.mvp.BuildConfig.DEBUG

/**
 * log管理类
 */
object LogUtils {
    /**
     * 是否打印输出
     */
    private val isPrint = false

    /**
     * 关闭或打开d（蓝色）输出
     */
    private val isPrint_d = true

    /**
     * 关闭或打开e（红色）输出
     */
    private val isPrint_e = true

    /**
     * 关闭或打开w（黄色）输出
     */
    private val isPrint_w = true

    /**
     * 关闭或打开i（绿色）输出
     */
    private val isPrint_i = true

    /**
     * 关闭或打开v（黑色）输出
     */
    private val isPrint_v = true

    /**
     * Log输出的前部分Tag
     */
    private val TAG = "APP"

    /**
     * 使用自定义TAG打印（颜色是蓝色）
     */
    fun d(tag: String, content: String) {
        if (DEBUG && isPrint_d) {
            Log.d(TAG + "-" + tag, content)
        }
    }

    /**
     * 使用默认TAG打印（颜色是蓝色）
     */
    fun d(content: String) {
        if (DEBUG && isPrint_d) {
            Log.d(TAG, content)
        }
    }

    /**
     * 使用自定义TAG打印（颜色为黑色）
     */
    fun v(tag: String, content: String) {
        if (DEBUG && isPrint_v) {
            Log.v(TAG + "-" + tag, content)
        }
    }

    /**
     * 使用默认TAG打印（颜色为黑色）
     */
    fun v(content: String) {
        if (DEBUG && isPrint_v) {
            Log.v(TAG, content)
        }
    }

    /**
     * 使用自定义TAG打印（颜色为黄色）
     */
    fun w(tag: String, content: String) {
        if (DEBUG && isPrint_w) {
            Log.w(TAG + "-" + tag, content)
        }
    }

    /**
     * 使用默认TAG打印（颜色为黄色）
     */
    fun w(content: String) {
        if (isPrint && isPrint_w) {
            Log.w(TAG, content)
        }
    }

    /**
     * 使用自定义TAG打印（颜色为红色）
     */
    fun e(tag: String, content: String) {
        if (DEBUG && isPrint_e) {
            Log.e(TAG + "-" + tag, content)
        }
    }

    /**
     * 使用默认TAG打印（颜色为红色）
     */
    fun e(content: String) {
        if (DEBUG && isPrint_e) {
            Log.e(TAG, content)
        }
    }

    /**
     * 使用自定义TAG打印（颜色为绿色）
     */
    fun i(tag: String, content: String) {
        if (DEBUG && isPrint_i) {
            Log.i(TAG + "-" + tag, content)
        }
    }

    /**
     * 使用默认TAG打印（颜色为绿色）
     */
    fun i(content: String) {
        if (isPrint && isPrint_i) {
            Log.i(TAG, content)
        }
    }

    /**
     * 打印长log
     */
    fun longE(tag: String, content: String) {
        var content = content
        if (DEBUG && isPrint_d) {
            val p = 2000
            val length = content.length.toLong()
            if (length <= p)
                Log.e(tag, content)
            else {
                while (content.length > p) {
                    val logContent = content.substring(0, p)
                    content = content.replace(logContent, "")
                    Log.e(tag, logContent)
                }
                Log.e(tag, content)
            }
        }
    }
}
