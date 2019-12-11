package com.daydaycook.mitao.config

import android.app.Dialog
import android.content.DialogInterface
import android.view.ViewTreeObserver

/**
 * Life Is Better With DayDayCook
 * author: HuangQiang
 * created on: 2018/12/18 18:12.
 * description:避免了内存泄露
 */
class DetachClickListener private constructor(private var mDelegate: DialogInterface.OnClickListener?) : DialogInterface.OnClickListener {

    override fun onClick(dialog: DialogInterface, which: Int) {
        if (mDelegate != null) {
            mDelegate!!.onClick(dialog, which)
        }
    }

    fun clearOnDetach(dialog: Dialog?) {
        dialog?.window?.decorView?.viewTreeObserver?.addOnWindowAttachListener(object : ViewTreeObserver.OnWindowAttachListener {
            override fun onWindowAttached() {}

            override fun onWindowDetached() {
                mDelegate = null
            }
        })
    }

    companion object {

        fun wrap(delegate: DialogInterface.OnClickListener): DetachClickListener {
            return DetachClickListener(delegate)
        }
    }

}

