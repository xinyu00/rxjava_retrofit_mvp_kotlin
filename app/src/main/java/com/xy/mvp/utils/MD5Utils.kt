package com.xy.mvp.utils

import android.text.TextUtils
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * anthor:Created by tianchen on 2017/3/27.
 * email:963181974@qq.com
 */

object MD5Utils {
    fun getEncryption(originString: String?): String {
        var result = ""
        if (TextUtils.isEmpty(originString)) {
            try {
                // 指定加密的方式为MD5
                val md = MessageDigest.getInstance("MD5")
                // 进行加密运算
                val bytes: ByteArray = md.digest(originString!!.toByteArray())
                for (aByte in bytes) {
                    // 将整数转换成十六进制形式的字符串 这里与0xff进行与运算的原因是保证转换结果为32位
                    var str = Integer.toHexString(aByte.toInt() and 0xFF)
                    if (str.length == 1) {
                        str += "F"
                    }
                    result += str
                }
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            }
        }
        return result
    }
}