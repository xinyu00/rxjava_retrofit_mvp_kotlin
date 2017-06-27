package com.xy.mvp.utils.constants

import com.facebook.common.util.ByteConstants

/**
 * Created by 陈新宇 on 2017/6/14.
 * App配置常量
 */

object ConfigConstants {
    //最大内存使用率
    private val MAX_HEAP_SIZE = Runtime.getRuntime().maxMemory().toInt()
    //最大磁盘缓存大小
    val MAX_DISK_CACHE_SIZE = 40 * ByteConstants.MB
    //最大内存缓存大小
    val MAX_MEMORY_CACHE_SIZE = MAX_HEAP_SIZE / 4
}
