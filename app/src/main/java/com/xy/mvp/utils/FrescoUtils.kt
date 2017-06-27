package com.xy.mvp.utils

import android.content.Context
import com.facebook.cache.disk.DiskCacheConfig
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory
import com.facebook.imagepipeline.cache.MemoryCacheParams
import com.xy.mvp.utils.constants.ConfigConstants
import okhttp3.OkHttpClient
import java.io.File

/**
 * Created by chenxy on 2017/6/23.
 */

object FrescoUtils {
    /**
     * 初始化Fresco
     * @param context ApplicationContext
     */
    fun initFresco(context: Context) {
        //初始化OkhttpClient
        val okHttpClient = OkHttpClient()
        //内存缓存配置
        val bitmapCacheParams = MemoryCacheParams(
                ConfigConstants.MAX_MEMORY_CACHE_SIZE, // Max total size of elements in the cache
                Integer.MAX_VALUE, // Max entries in the cache
                ConfigConstants.MAX_MEMORY_CACHE_SIZE, // Max total size of elements in eviction queue
                Integer.MAX_VALUE, // Max length of eviction queue
                Integer.MAX_VALUE)                    // Max cache entry size

        val config = OkHttpImagePipelineConfigFactory.newBuilder(context.applicationContext, okHttpClient)
                .setBitmapMemoryCacheParamsSupplier { bitmapCacheParams }
                .setMainDiskCacheConfig(//设置磁盘缓存
                        DiskCacheConfig.newBuilder(context.applicationContext)
                                .setBaseDirectoryPath(File(FileUtils.appFilePath + "/image/")) // 注意Android运行时权限。
                                .setMaxCacheSize(ConfigConstants.MAX_DISK_CACHE_SIZE.toLong())
                                .build()
                )
                .build()
        Fresco.initialize(context.applicationContext, config)     //初始化Fresco
    }

    /**
     * 清除Fresco缓存
     */
    fun clearCache() {
        val imagePipeline = Fresco.getImagePipeline()
        imagePipeline.clearCaches()
        imagePipeline.clearDiskCaches()
        imagePipeline.clearMemoryCaches()
    }
}
