package com.xy.mvp.presenter.api

import com.xy.mvp.utils.constants.UrlUtils

import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

/**
 * anthor:Created by tianchen on 2017/3/27.
 * email:963181974@qq.com
 */

interface ApiService {
    @GET(UrlUtils.USER)
    fun rxlogin(
            @Header("Cache-Control") cacheControl: String,
            @Query("username")
            username: String,
            @Query("password")
            password: String,
            @Query("type")
            type: Int): Flowable<String>

    @GET(UrlUtils.USER)
    fun register(
            @Query("phone")
            phone: String,
            @Query("password")
            password: String,
            @Query("role")
            role: Int,
            @Query("type")
            type: Int): Flowable<String>

    @GET(UrlUtils.TAOBAOURL)
    fun getIp(
            @Query("ip")
            ip: String): Flowable<String>

}
