package com.xy.mvp.base

import android.app.Activity
import java.util.*

/**
 * anthor:Created by tianchen on 2017/3/29.
 * email:963181974@qq.com
 * APP统一管理
 */

class AppManager private constructor() {
    private var activityStack: Stack<Activity>? = null

    /**
     * 添加Activity到堆栈
     */
    fun addActivity(activity: Activity) {
        if (activityStack == null) {
            activityStack = Stack<Activity>()
        }
        activityStack!!.add(activity)
    }

    /**
     * 获取当前Activity (堆栈中最后一个压入的)
     */
    fun currentActivity(): Activity {
        return activityStack!!.lastElement()
    }

    /**
     * 结束指定的Activity
     * @param activity 要结束的Activity
     */
    fun finishActivity(activity: Activity? = currentActivity()) {
        if (activity != null && !activity.isFinishing) {
            activityStack!!.remove(activity)
            activity.finish()
        }
    }

    /**
     * 结束指定类名的Activity
     */
    fun finishActivity(cls: Class<*>) {
        for (activity in activityStack!!) {
            if (activity.javaClass == cls) {
                finishActivity(activity)
                break
            }
        }
    }

    /**
     * 结束所有Activity
     */
    private fun finishAllActivity() {
        for (i in activityStack!!.indices) {
            if (null != activityStack!![i]) {
                activityStack!![i].finish()
            }
        }
        activityStack!!.clear()
    }

    /**
     * 获取指定的Activity
     */
    fun getActivity(cls: Class<*>): Activity? {
        if (activityStack != null) {
            for (activity in activityStack!!) {
                if (activity.javaClass == cls) {
                    return activity
                }
            }
        }
        return null
    }

    /**
     * 退出App
     */
    fun AppExit() {
        try {
            finishAllActivity()
            android.os.Process.killProcess(android.os.Process.myPid())
            System.exit(0)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
        }
    }

    /**
     * 结束当前Activity(堆栈中最后一个压入的)
     */
    fun finishActivity() {
        finishActivity(currentActivity())
    }

    companion object {
        /**
         * 单一实例化当前对象
         */
        fun getAppManager(): AppManager {
            return Holder.instance
        }
    }

    private object Holder {
        val instance = AppManager()
    }
}
