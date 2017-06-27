package com.xy.mvp.ui.user

import android.Manifest
import android.content.Intent
import android.widget.Button
import butterknife.BindView
import butterknife.OnClick
import com.facebook.drawee.view.SimpleDraweeView
import com.xy.mvp.R
import com.xy.mvp.base.BaseActivity
import com.xy.mvp.utils.FileUtils

/**
 * 用户选择页 （登陆/注册）
 */
class UserSelectUI : BaseActivity() {

    @BindView(R.id.iv_content)
    lateinit var iv_content: SimpleDraweeView

    override fun initView() {

    }

    override fun initData() {
        baseActivityPresenter!!.requestPermission(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 6666)
        //        iv_content.setImageResource(R.mipmap.ic_launcher);
        iv_content!!.setImageResource(R.mipmap.ic_launcher)
    }

    override fun permissionSuccess(requestCode: Int) {
        super.permissionSuccess(requestCode)
        FileUtils.createAppFile()
    }

    override val layoutId: Int get() = R.layout.activity_user_select

    @OnClick(R.id.bt_login, R.id.bt_new_user)
    fun onClick(button: Button) {
        val intent = Intent()
        when (button.id) {
            R.id.bt_login -> {
                intent.setClass(this, LoginUI::class.java)
                startActivity(intent)
            }
            R.id.bt_new_user -> {
                intent.setClass(this, RegisterUI::class.java)
                startActivity(intent)
            }
        }
    }
}
