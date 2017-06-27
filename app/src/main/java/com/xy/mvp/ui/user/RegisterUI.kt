package com.xy.mvp.ui.user

import android.app.ProgressDialog
import android.text.TextUtils
import android.widget.EditText
import butterknife.BindView
import butterknife.OnClick
import com.xy.mvp.R
import com.xy.mvp.base.BaseActivity
import com.xy.mvp.dagger.component.DaggerActivityComponent
import com.xy.mvp.presenter.user.RegisterUIPresenter
import com.xy.mvp.utils.ToastUtils
import javax.inject.Inject

/**
 * 注册页
 */
class RegisterUI : BaseActivity() {
    @BindView(R.id.et_phone)
    lateinit var et_phone: EditText
    @BindView(R.id.et_password)
    lateinit var et_password: EditText

    private var dialog: ProgressDialog? = null

    @Inject
    lateinit var presenter: RegisterUIPresenter

    override fun initView() {
        dialog = ProgressDialog(this)
    }

    override fun initData() {
        initInject()
    }

    private fun initInject() {
        DaggerActivityComponent.builder()
                .activityModule(activityModule)
                .build()
                .inject(this)
    }

    override val layoutId: Int
        get() = R.layout.activity_register

    fun success(msg: String?) {
        dialog!!.dismiss()
        ToastUtils.showShort(this, "尊敬的" + et_phone!!.text.toString() + "用户," + msg)
    }

    private fun checkUserInfo(username: String, password: String): Boolean {
        return !TextUtils.isEmpty(username) || !TextUtils.isEmpty(password)
    }

    fun failed(msg: String?) {
        dialog!!.dismiss()
        ToastUtils.errorShow(this, msg)
    }

    @OnClick(R.id.bt_register)
    fun onClick() {
        val phone = et_phone!!.text.toString()
        val password = et_password!!.text.toString()
        val checkUserInfo = checkUserInfo(phone, password)
        if (checkUserInfo) {
            dialog!!.show()
            presenter!!.register(et_phone!!.text.toString(), et_password!!.text.toString())
        } else {
            ToastUtils.showShort(this, "手机号或密码不能为空")
        }
    }
}
