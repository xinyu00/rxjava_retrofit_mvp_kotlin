package com.xy.mvp.base

import android.app.Application
import com.squareup.leakcanary.LeakCanary
import com.xy.mvp.BuildConfig
import com.xy.mvp.utils.FrescoUtils

/**
 * anthor:Created by tianchen on 2017/2/13.
 * email:963181974@qq.com
 */

class AndroidApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        instance = this
        initEnv()

    }


    /**
     * 初始化运行环境
     */
    private fun initEnv() {
        //初始化Fresco
        FrescoUtils.initFresco(this)
        //LeakCanary内存泄露检测
        if (BuildConfig.DEBUG) {
            LeakCanary.install(this)
        }
    }

    companion object {
        private var instance: Application? = null

        fun getAppContext() = instance!!
    }

}
