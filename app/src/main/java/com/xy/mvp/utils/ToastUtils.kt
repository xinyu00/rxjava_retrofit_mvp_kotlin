package com.xy.mvp.utils


import android.content.Context
import android.view.Gravity
import android.widget.Toast

import com.xy.mvp.BuildConfig.DEBUG

/**
 * Toast统一管理类
 */
class ToastUtils private constructor() {

    init {
        throw UnsupportedOperationException("toastutils cannot be new")
    }

    companion object {
        //Toast弹出标识
        var isShow = true
            set
        private var toast: Toast? = null

        /**
         * 短时间显示Toast
         */
        fun showShort(context: Context, message: CharSequence?) {
            if (isShow) {
                cancel()
                toast = Toast.makeText(context.applicationContext, message, Toast.LENGTH_SHORT)
                toast!!.show()
            }
        }

        /**
         * 弹出提示框
         */
        private fun cancel() {
            if (toast != null) {
                toast!!.cancel()
                toast = null
            }
        }


        /**
         * 短时间显示Toast
         */
        fun showShort(context: Context, message: Int) {
            if (isShow) {
                cancel()
                toast = Toast.makeText(context.applicationContext, message, Toast.LENGTH_SHORT)
                toast!!.show()
            }
        }

        /**
         * 长时间显示Toast
         */
        fun showLong(context: Context, message: CharSequence?) {
            if (isShow) {
                cancel()
                toast = Toast.makeText(context.applicationContext, message, Toast.LENGTH_LONG)
                toast!!.show()
            }
        }

        /**
         * 长时间显示Toast
         */
        fun showLong(context: Context, message: Int) {
            if (isShow) {
                cancel()
                toast = Toast.makeText(context.applicationContext, message, Toast.LENGTH_LONG)
                toast!!.show()
            }
        }

        /**
         * 自定义显示Toast时间
         */
        fun show(context: Context, message: CharSequence?, duration: Int) {
            if (isShow) {
                cancel()
                toast = Toast.makeText(context.applicationContext, message, duration)
                toast!!.show()
            }
        }

        /**
         * 自定义显示Toast时间
         */
        fun show(context: Context, message: Int, duration: Int) {
            if (isShow) {
                cancel()
                toast = Toast.makeText(context.applicationContext, message, duration)
                toast!!.show()
            }
        }

        /**
         * 错误提示 （可用于debug模式下网络请求错误提示，replease模式下自动关闭）
         */
        fun errorShow(context: Context, message: CharSequence?) {
            if (DEBUG) {
                showShort(context.applicationContext, message)
            }
        }

        /**
         * 自定义显示位置 居中
         */
        fun centerShow(context: Context, message: CharSequence?) {
            if (isShow) {
                cancel()
                toast = Toast.makeText(context.applicationContext, message, Toast.LENGTH_SHORT)
                toast!!.setGravity(Gravity.CENTER, 0, 0)
                toast!!.show()
            }
        }
    }
}