package com.xy.mvp.utils

/**
 * anthor:Created by tianchen on 2017/2/17.
 * email:963181974@qq.com
 */

object JniTest {
    init {
        System.loadLibrary("native-lib")
    }

    external fun getStockMarketCode(code: String): String
}
