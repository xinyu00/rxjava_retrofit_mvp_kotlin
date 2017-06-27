package com.xy.mvp.ui.user

import android.app.ProgressDialog
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import butterknife.BindView
import com.xy.mvp.R
import com.xy.mvp.base.BaseActivity
import com.xy.mvp.dagger.component.DaggerActivityComponent
import com.xy.mvp.presenter.user.LoginUIPresenter
import com.xy.mvp.utils.ToastUtils
import javax.inject.Inject

/**
 * 登陆页
 */
class LoginUI : BaseActivity() {

    @BindView(R.id.et_username)
    lateinit var mUsername: EditText
    @BindView(R.id.et_password)
    lateinit var mPassword: EditText

    private var dialog: ProgressDialog? = null

    @Inject
    lateinit  var presenter: LoginUIPresenter

    override fun initData() {
        dialog = ProgressDialog(this)
        initInject()
    }

    internal var i = 0

    //按钮点击
    fun login(view: View) {
        ToastUtils.errorShow(this, "吐司" + ++i)
        val username = mUsername.text.toString()
        val password = mPassword.text.toString()
        val checkUserInfo = checkUserInfo(username, password)
        if (checkUserInfo) {
            dialog!!.show()
            presenter.login(username, password)
        } else {
            ToastUtils.showShort(this, "用户名或密码不能为空" + ++i)
        }
    }

    private fun initInject() {
        DaggerActivityComponent.builder()
                .activityModule(activityModule)
                .build()
                .inject(this)
    }

    private fun checkUserInfo(username: String, password: String): Boolean {
        return !TextUtils.isEmpty(username) || !TextUtils.isEmpty(password)
    }

    fun success() {
        dialog!!.dismiss()
        ToastUtils.showShort(this, "欢迎回来：" + mUsername.text.toString())
    }

    fun failed() {
        dialog!!.dismiss()
        ToastUtils.showShort(this, "用户名或密码输入有误")
    }

    override fun initView() {
        setTopColor(ContextCompat.getColor(this, R.color.button_nor))
    }

    override val layoutId: Int
        get() = R.layout.activity_login

}
