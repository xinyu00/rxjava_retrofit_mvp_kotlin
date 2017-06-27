package com.xy.mvp.utils

import android.content.Context
import android.content.SharedPreferences

import com.alibaba.fastjson.JSON

/**
 * 通过SharedPreferences存取数据的工具类
 */
class SpUtils {

    /**
     * 清除sp
     */
    fun clearAll(context: Context) {
        initSp(context)
        sharedPreferences!!.edit().clear().apply()
    }

    companion object {
        private var sharedPreferences: SharedPreferences? = null
        private val config = "XY"
        private val TAG = "SpUtils"

        //初始化sp
        private fun initSp(context: Context) {
            if (sharedPreferences == null) {
                sharedPreferences = context.getSharedPreferences(config, Context.MODE_PRIVATE)
            }
        }

        /**
         * 存储String数据
         */
        fun put(context: Context, Key: String, Value: String): Boolean {
            initSp(context)
            return sharedPreferences!!.edit().putString(Key, Value).commit()
        }

        /**
         * 存储boolean值
         */
        fun put(context: Context, key: String, value: Boolean): Boolean {
            initSp(context)
            return sharedPreferences!!.edit().putBoolean(key, value).commit()
        }

        /**
         * 存储int值
         */
        fun put(context: Context, key: String, value: Int): Boolean {
            initSp(context)
            return sharedPreferences!!.edit().putInt(key, value).commit()
        }

        /**
         * 存储对象json
         */
        fun put(context: Context, key: String, `object`: Any): Boolean {
            initSp(context)
            return sharedPreferences!!.edit().putString(key, JSON.toJSONString(`object`)).commit()
        }

        /**
         * 拿取String数据
         */
        operator fun get(context: Context, Key: String, defvalue: String): String {
            initSp(context)
            return sharedPreferences!!.getString(Key, defvalue)
        }

        //

        /**
         * 取int值
         */
        operator fun get(context: Context, key: String, defValue: Int): Int {
            initSp(context)
            return sharedPreferences!!.getInt(key, defValue)
        }

        /**
         * 取boolean值
         */
        operator fun get(context: Context, key: String, defValue: Boolean): Boolean {
            initSp(context)
            return sharedPreferences!!.getBoolean(key, defValue)
        }

        /**
         * 取对象
         */
        operator fun <T> get(context: Context, key: String, clazz: Class<T>): T {
            initSp(context)
            return JSON.parseObject(sharedPreferences!!.getString(key, ""), clazz)
        }
    }

}