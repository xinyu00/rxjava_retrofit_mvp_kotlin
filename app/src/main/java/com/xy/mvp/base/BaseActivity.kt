package com.xy.mvp.base

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import butterknife.ButterKnife
import butterknife.Unbinder
import com.xy.mvp.dagger.component.DaggerActivityComponent
import com.xy.mvp.dagger.module.ActivityModule
import com.xy.mvp.presenter.BaseActivityPresenter
import com.xy.mvp.utils.AppCompatUtils
import com.xy.mvp.utils.LogUtils
import com.xy.mvp.utils.ToastUtils
import javax.inject.Inject

/**
 * anthor:Created by tianchen on 2017/3/27.
 * email:963181974@qq.com
 */
abstract class BaseActivity : AppCompatActivity() {
    // 返回按钮按下时间
    private var currentBackPressedTime: Long = 0
    //ButterKnife
    private var unbinder: Unbinder? = null
    //onStart方法实现只执行一次标识
    private var onStartFlag: Boolean = false
    //Log标识
    private val TAG = "MPermissions"
    //Activity布局
    private var ll_content: LinearLayout? = null
    //沉浸式标题头
    private var top: View? = null
    //窗体

    @Inject
    lateinit var baseActivityPresenter: BaseActivityPresenter

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onStartFlag = true
        //初始化baseActivityPresenter层
        DaggerActivityComponent.builder()
                .activityModule(activityModule)
                .build()
                .inject(this)
        //设置适配布局
        setContentView()
        //隐藏ActionBar
        setActionBar()
        //隐藏状态栏
        setTopShow(false)
        //Activity管理
        AppManager.getAppManager().addActivity(this)
        //初始化布局
        initView()
    }

    private fun setActionBar() {
        val actionBar = supportActionBar
        actionBar?.hide()
    }

    /**
     * 请求权限回调
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == baseActivityPresenter!!.REQUEST_CODE_PERMISSION) {
            if (baseActivityPresenter!!.verifyPermissions(grantResults)) {
                permissionSuccess(baseActivityPresenter!!.REQUEST_CODE_PERMISSION)
            } else {
                permissionFail(baseActivityPresenter!!.REQUEST_CODE_PERMISSION)
                baseActivityPresenter!!.showTipsDialog()
            }
        }
    }

    /**
     * 设置沉浸式头布局
     */
    protected fun setImmersion(): View? {
        // 判断SDK的Api是否大于19
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            top = View(this)
            //设置沉浸式标题栏
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            val topparams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, AppCompatUtils.dp2px(25, this))
            top!!.layoutParams = topparams
            top!!.setBackgroundColor(0x21baf5)
        }
        return top
    }

    /**
     * 沉浸式时，设置标题栏布局是否隐藏

     * @param flag false:隐藏标题栏 true:移出标题栏
     */
    protected fun setTopShow(flag: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            if (flag) {
                top!!.visibility = View.VISIBLE
            } else {
                top!!.visibility = View.GONE
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (flag) {
                window!!.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            } else {
                window!!.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            }
        }
    }

    /**
     * 初始化布局
     */
    protected fun setContentView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            ll_content = LinearLayout(this)
            //设置当前Activity布局填充满屏幕
            ll_content!!.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
            ll_content!!.orientation = LinearLayout.VERTICAL   //竖直布局
            setContentView(ll_content)     //Activity填充布局
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setContentView(layoutId)
        } else {
            setContentView(layoutId)
        }

    }

    /**
     * 设置ll_content的子布局
     */
    protected fun setSonView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            ll_content!!.addView(setImmersion())
            //获取子类布局
            val content = LayoutInflater.from(this).inflate(layoutId, null) as ViewGroup
            content.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            //添加主体布局
            ll_content!!.addView(content)
        }
    }

    /**
     * 获取权限成功

     * @param requestCode 获取权限成功后返回的code值
     */
    open fun permissionSuccess(requestCode: Int) {
        LogUtils.e(TAG, "获取权限成功=" + requestCode)
    }

    /**
     * 权限获取失败

     * @param requestCode 获取权限失败后返回的code值
     */
    fun permissionFail(requestCode: Int) {
        LogUtils.e(TAG, "获取权限失败=" + requestCode)
    }

    /**
     * 获取ActivityModule

     * @return dagger2 module层
     */
    protected val activityModule: ActivityModule
        get() = ActivityModule(this)

    override fun onStart() {
        super.onResume()
        if (onStartFlag) {
            //设置沉浸式标题栏
            setSonView()
            //初始化ButterKnife注解框架
            unbinder = ButterKnife.bind(this)
            //初始化数据
            initData()
            onStartFlag = false
        }
    }

    /**
     * 设置top颜色
     */
    protected fun setTopColor(color: Int) {
        setTopShow(true)
        if (top != null) {
            top!!.setBackgroundResource(color)
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window!!.statusBarColor = color
            }
        }
    }

    /**
     * 默认Activity返回键退出Activity
     */
    override fun onBackPressed() {
        AppManager.getAppManager().finishActivity()
    }

    /**
     * 保留方法 (初始化布局,目前ButterKnife实现)
     */
    abstract fun initView()

    /**
     * 初始化数据
     */
    abstract fun initData()

    /**
     * 用于获取布局的id

     * @return 布局id
     */
    abstract val layoutId: Int

    /**
     * 设置当前Activity二次点击退出整个APP
     */
    protected fun secondClickFinish() {
        if (System.currentTimeMillis() - currentBackPressedTime > BACK_PRESSED_INTERVAL) {
            currentBackPressedTime = System.currentTimeMillis()
            ToastUtils.showShort(this, "再按一次返回键退出程序")
        } else {
            // 退出
            ToastUtils.isShow = false
            AppManager.getAppManager().AppExit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbinder!!.unbind()
    }

    companion object {
        // 退出间隔
        private val BACK_PRESSED_INTERVAL = 2000
    }
}
