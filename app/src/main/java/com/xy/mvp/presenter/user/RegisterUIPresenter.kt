package com.xy.mvp.presenter.user

import com.alibaba.fastjson.JSON
import com.xy.mvp.dagger.PerActivity
import com.xy.mvp.entity.ResponseInfo
import com.xy.mvp.presenter.api.Api
import com.xy.mvp.presenter.api.ApiService
import com.xy.mvp.presenter.api.HostType
import com.xy.mvp.ui.user.RegisterUI
import com.xy.mvp.utils.constants.UrlUtils

import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

import java.util.ArrayList

import javax.inject.Inject

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * anthor:Created by tianchen on 2017/4/23.
 * email:963181974@qq.com
 */
@PerActivity
class RegisterUIPresenter @Inject
constructor(private val activity: RegisterUI) {
    private val api: ApiService
    private val subscriptions: MutableList<Subscription>

    init {
        api = Api.getDefault(HostType.TYPE1, UrlUtils.BASEURL)
        subscriptions = ArrayList<Subscription>()
    }

    /**
     * 用户注册
     */
    fun register(phone: String, password: String) {
        api.register(phone, password, 0, 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<String> {
                    override fun onError(e: Throwable) {
                        activity.failed(e.toString())
                    }

                    override fun onComplete() {

                    }

                    override fun onSubscribe(s: Subscription) {
                        s.request(java.lang.Long.MAX_VALUE)
                        subscriptions.add(s)
                    }

                    override fun onNext(s: String) {
                        val info = JSON.parseObject(s, ResponseInfo::class.java)
                        if ("0" == info.code) {
                            activity.success(info.msg)
                        } else {
                            activity.failed(info.msg)
                        }
                    }
                })
    }

    fun clearNet() {
        for (s in subscriptions) {
            s.cancel()
        }
    }
}
