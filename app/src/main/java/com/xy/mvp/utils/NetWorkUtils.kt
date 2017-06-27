package com.xy.mvp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.xy.mvp.presenter.api.Api
import com.xy.mvp.presenter.api.HostType

import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

import java.util.regex.Matcher
import java.util.regex.Pattern

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * anthor:Created by tianchen on 2017/3/27.
 * email:963181974@qq.com
 */

object NetWorkUtils {
    /**
     * 检查网络是否可用
     */
    fun isNetConnected(context: Context): Boolean {
        val connectivity = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivity != null) {
            val info = connectivity.activeNetworkInfo
            if (info != null && info.isConnected) {
                // 当前网络是连接的
                if (info.state == NetworkInfo.State.CONNECTED) {
                    // 当前所连接的网络可用
                    return true
                }
            }
        }
        return false
    }

    /**
     * 检测wifi是否连接
     */
    fun isWifiConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (cm != null) {
            val networkInfo = cm.activeNetworkInfo
            if (networkInfo != null && networkInfo.type == ConnectivityManager.TYPE_WIFI) {
                return true
            }
        }
        return false
    }

    /**
     * 检测GPRS是否连接
     */
    fun isGPRSConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (cm != null) {
            val networkInfo = cm.activeNetworkInfo
            if (networkInfo != null && networkInfo.type == ConnectivityManager.TYPE_MOBILE) {
                return true
            }
        }
        return false
    }

    /**
     * 判断网址是否有效
     */
    fun isLinkAvailable(link: String): Boolean {
        val regex = "^(http://|https://)?((?:[A-Za-z0-9]+-[A-Za-z0-9]+|[A-Za-z0-9]+)\\.)+([A-Za-z]+)[/\\?\\:]?.*$"
        val pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(link)
        return matcher.matches()
    }


    /**
     * 将得到的int类型的IP转换为String类型

     * @param ip int类型ip地址
     * *
     * @return String类型ip地址
     */
    fun intIP2StringIP(ip: Int): String {
        return (ip and 0xFF).toString() + "." +
                (ip shr 8 and 0xFF) + "." +
                (ip shr 16 and 0xFF) + "." +
                (ip shr 24 and 0xFF)
    }
}
