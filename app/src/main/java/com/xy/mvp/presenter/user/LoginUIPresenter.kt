package com.xy.mvp.presenter.user

import android.text.TextUtils
import android.util.Log

import com.xy.mvp.dagger.PerActivity
import com.xy.mvp.presenter.api.Api
import com.xy.mvp.presenter.api.ApiService
import com.xy.mvp.presenter.api.HostType
import com.xy.mvp.ui.user.LoginUI
import com.xy.mvp.utils.constants.UrlUtils

import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

import java.util.ArrayList

import javax.inject.Inject

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * anthor:Created by tianchen on 2017/2/13.
 * email:963181974@qq.com
 */
@PerActivity
class LoginUIPresenter @Inject
constructor(private val activity: LoginUI) {
    private val api: ApiService
    private val subscriptions: MutableList<Subscription>

    init {
        api = Api.getDefault(HostType.TYPE1, UrlUtils.BASEURL)
        subscriptions = ArrayList<Subscription>()
    }

    /**
     * 用户登录
     */
    fun login(username: String, password: String) {
        api.rxlogin(Api.cacheControl, username, password, 3)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<String> {
                    override fun onError(e: Throwable) {
                        Log.e("异常", e.toString())
                        activity.failed()
                    }

                    override fun onComplete() {

                    }

                    override fun onSubscribe(s: Subscription) {
                        s.request(java.lang.Long.MAX_VALUE)
                        subscriptions.add(s)
                    }

                    override fun onNext(s: String) {
                        if (!TextUtils.isEmpty(s)) {
                            Log.e("json串", s)
                            activity.success()
                        } else {
                        }
                    }
                })
    }

    fun cancleNet() {
        for (subscription in subscriptions) {
            subscription.cancel()
        }
    }
}
