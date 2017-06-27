package com.xy.mvp.ui

import com.xy.mvp.R
import com.xy.mvp.base.BaseActivity

/**
 * 主界面
 */
class IndexUI : BaseActivity() {

    override fun initView() {
        secondClickFinish()
    }

    override fun initData() {

    }

    override val layoutId: Int
        get() = R.layout.activity_index

}
