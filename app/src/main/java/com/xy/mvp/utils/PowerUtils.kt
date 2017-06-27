package com.xy.mvp.utils

import android.annotation.TargetApi
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import java.util.*

/**
 * anthor:Created by tianchen on 2017/5/14.
 * email:963181974@qq.com
 * Android6.0 动态权限处理类(已废弃)
 */

class PowerUtils {

    private var REQUEST_CODE_PERMISSION = 1
    private val TAG = "0x00099"

    /**
     * 检测所有的权限是否都已授权
     * @param permissions 权限列表
     * *
     * @return 是否全部授权 true 已授权 false 未授权
     */
    private fun checkPermissions(context: Context, permissions: Array<String>): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true
        }

        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    /**
     * 获取权限集中需要申请权限的列表
     * @param permissions 权限列表
     * *
     * @return  返回未获取到权限的列表集合
     */
    private fun getDeniedPermissions(context: Activity, permissions: Array<String>): List<String> {
        val needRequestPermissionList = ArrayList<String>()
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED || ActivityCompat.shouldShowRequestPermissionRationale(context, permission)) {
                needRequestPermissionList.add(permission)
            }
        }
        return needRequestPermissionList
    }


    /**
     * 请求权限

     * @param permissions 请求的权限
     * *
     * @param requestCode 请求权限的请求码
     */
    @TargetApi(value = Build.VERSION_CODES.M)
    fun requestPermission(activity: Activity, permissions: Array<String>, requestCode: Int) {
        this.REQUEST_CODE_PERMISSION = requestCode
        if (checkPermissions(activity, permissions)) {
            permissionSuccess(REQUEST_CODE_PERMISSION)
        } else {
            val needPermissions = getDeniedPermissions(activity, permissions)
            ActivityCompat.requestPermissions(activity, needPermissions.toTypedArray(), REQUEST_CODE_PERMISSION)
        }
    }


    /**
     * 获取权限成功

     * @param requestCode 请求码
     */
    fun permissionSuccess(requestCode: Int) {
        LogUtils.e(TAG, "获取权限成功=" + requestCode)
    }


    /**
     * 权限获取失败

     * @param requestCode 请求码
     */
    fun permissionFail(requestCode: Int) {
        LogUtils.e(TAG, "获取权限失败=" + requestCode)
    }

    /**
     * 显示提示对话框
     */
    private fun showTipsDialog(activity: Activity) {
        AlertDialog.Builder(activity)
                .setTitle("提示信息")
                .setMessage("当前应用缺少必要权限，该功能暂时无法使用。如若需要，请单击【确定】按钮前往设置中心进行权限授权。")
                .setNegativeButton("取消") { dialog, which -> }
                .setPositiveButton("确定") { dialog, which -> startAppSettings(activity) }.show()
    }


    /**
     * 启动当前应用设置页面
     */
    private fun startAppSettings(activity: Activity) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.parse("package:" + activity.packageName)
        activity.startActivity(intent)
    }

    companion object {
        private var power: PowerUtils? = null

        val instance: PowerUtils?
            get() {
                if (power == null) {
                    synchronized(PowerUtils::class.java) {
                        if (power == null) {
                            power = PowerUtils()
                        }
                    }
                }
                return power
            }
    }
}
