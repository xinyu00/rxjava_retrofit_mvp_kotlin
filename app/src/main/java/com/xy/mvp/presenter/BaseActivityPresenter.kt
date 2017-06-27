package com.xy.mvp.presenter

import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.xy.mvp.base.BaseActivity
import com.xy.mvp.dagger.PerActivity
import java.util.*
import javax.inject.Inject

/**
 * anthor:Created by tianchen on 2017/5/14.
 * email:963181974@qq.com
 * BaseActivity的Presenter层
 */
@PerActivity
class BaseActivityPresenter @Inject
constructor(private val activity: BaseActivity) {

    //权限请求默认标识
    var REQUEST_CODE_PERMISSION = 0x0009

    /**
     * 确认所有的权限是否都已授权
     */
    fun verifyPermissions(grantResults: IntArray): Boolean {
        for (grantResult in grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    /**
     * 检测所有的权限是否都已授权
     */
    private fun checkPermissions(permissions: Array<String>): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true
        }

        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    /**
     * 获取权限集中需要申请权限的列表
     */
    private fun getDeniedPermissions(permissions: Array<String>): List<String> {
        val needRequestPermissionList = ArrayList<String>()
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED || ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
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
    fun requestPermission(permissions: Array<String>, requestCode: Int) {
        this.REQUEST_CODE_PERMISSION = requestCode
        if (checkPermissions(permissions)) {
            activity.permissionSuccess(REQUEST_CODE_PERMISSION)
        } else {
            val needPermissions = getDeniedPermissions(permissions)
            ActivityCompat.requestPermissions(activity, needPermissions.toTypedArray(), REQUEST_CODE_PERMISSION)
        }
    }

    /**
     * 显示提示对话框
     */
    fun showTipsDialog() {
        AlertDialog.Builder(activity)
                .setTitle("提示信息")
                .setMessage("当前应用缺少必要权限，该功能暂时无法使用。如若需要，请单击【确定】按钮前往设置中心进行权限授权。")
                .setNegativeButton("取消") { dialog, which -> }
                .setPositiveButton("确定") { dialog, which -> startAppSettings() }.show()
    }


    /**
     * 启动当前应用设置页面
     */
    private fun startAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.parse("package:" + activity.packageName)
        activity.startActivity(intent)
    }

}
