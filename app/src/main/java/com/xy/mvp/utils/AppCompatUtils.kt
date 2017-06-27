package com.xy.mvp.utils

import android.content.Context

/**
 * anthor:Created by tianchen on 2017/3/28.
 * email:963181974@qq.com
 * App兼容适配工具类
 */

object AppCompatUtils {
    /**
     * dp转换px
     * @param dp    dp值
     * *
     * @param context   上下文
     * *
     * @return  转换后的px值
     */
    fun dp2px(dp: Int, context: Context): Int {
        // px = dp * 密度比
        val density = context.resources.displayMetrics.density // 0.75 1 1.5 2
        return (dp * density + 0.5f).toInt()// 4舍5入
    }
}
