package com.xy.mvp.utils;

/**
 * anthor:Created by tianchen on 2017/2/17.
 * email:963181974@qq.com
 * 调用jni工具类
 */

public class JniUtils {
    static {
        System.loadLibrary("native-lib");
    }
   public native static String getStockMarketCode(String code);
}
