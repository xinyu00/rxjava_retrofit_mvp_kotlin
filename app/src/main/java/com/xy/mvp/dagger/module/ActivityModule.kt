package com.xy.mvp.dagger.module

import android.app.Activity

import com.xy.mvp.base.BaseActivity
import com.xy.mvp.dagger.PerActivity
import com.xy.mvp.presenter.BaseActivityPresenter
import com.xy.mvp.presenter.user.LoginUIPresenter
import com.xy.mvp.presenter.user.RegisterUIPresenter
import com.xy.mvp.ui.user.LoginUI
import com.xy.mvp.ui.user.RegisterUI

import dagger.Module
import dagger.Provides

/**
 * anthor:Created by tianchen on 2017/2/13.
 * email:963181974@qq.com
 */

@Module
class ActivityModule(val activity: Activity) {

    @Provides
    @PerActivity
    internal fun activity(): Activity {
        return this.activity
    }

    @Provides
    @PerActivity
    internal fun provideLoginPresenter(): LoginUIPresenter {
        return LoginUIPresenter(activity() as LoginUI)
    }

    @Provides
    @PerActivity
    internal fun provideRegisterPresenter(): RegisterUIPresenter {
        return RegisterUIPresenter(activity() as RegisterUI)
    }

    @Provides
    @PerActivity
    internal fun provideBasePresenter(): BaseActivityPresenter {
        return BaseActivityPresenter(activity() as BaseActivity)
    }
}
