package com.xy.mvp.view

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.TextView

import com.xy.mvp.R
import com.xy.mvp.base.AppManager

/**
 * anthor:Created by tianchen on 2017/4/21.
 * email:963181974@qq.com
 */

class MessageDialog private constructor(context: Context = AppManager.getAppManager().currentActivity(), themeResId: Int = R.style.Hintdialog) : Dialog(context, themeResId), View.OnClickListener {
    private var tvInfo: TextView? = null
    private var backListener: BackListener? = null

    fun showDialog(msg: String) {
        this.showDialog(msg, null)
    }

    fun showDialog(msg: String, backListener: BackListener?) {
        this.backListener = backListener
        if (content != null) {
            setDialog()
            tvInfo!!.text = msg
        }
    }

    private fun setDialog() {
        Inner.msgDialog.show()
        Inner.msgDialog.setCancelable(false)
        Inner.msgDialog.setContentView(dialogView)
    }

    private //        tvTitle = (TextView) view.findViewById(R.id.tv_title);
    val dialogView: View
        get() {
            val wm = content!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val dm = DisplayMetrics()
            wm.defaultDisplay.getMetrics(dm)
            val width = dm.widthPixels
            val height = dm.heightPixels
            val minWidth = (width * 0.7).toInt()
            val minHeight = (height * 0.3).toInt()
            val view = LayoutInflater.from(content).inflate(R.layout.dialog_hint, null)
            view.minimumWidth = minWidth
            view.minimumHeight = minHeight
            tvInfo = view.findViewById(R.id.iv_content) as TextView
            val tvBack = view.findViewById(R.id.tv_back) as TextView
            tvBack.setOnClickListener(this)
            return view
        }

    private val content: Activity?
        get() = AppManager.getAppManager().currentActivity()

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_back -> {
                Inner.msgDialog.dismiss()
                backListener!!.doBack()
            }
        }
    }

    interface BackListener {
        fun doBack()
    }

    companion object {

        fun getInstance(): MessageDialog {
            return Inner.msgDialog
        }
    }

    private object Inner {
        var msgDialog = MessageDialog()
    }


}
