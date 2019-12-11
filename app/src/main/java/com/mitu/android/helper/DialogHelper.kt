package com.mitu.android.helper

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import com.mitu.android.R

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2018/01/10
 * desc  : 对话框工具类
</pre> *
 */
object DialogHelper {

    fun showRationaleDialog(activity: Activity?, message: String, positiveListener: DialogInterface.OnClickListener, negativeListener: DialogInterface.OnClickListener) {

        if (activity == null || activity.isFinishing) return
        AlertDialog.Builder(activity, R.style.Theme_AppCompat_Light_Dialog_Alert)
                .setTitle(android.R.string.dialog_alert_title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, positiveListener)
                .setNegativeButton(android.R.string.cancel, negativeListener)
                .setCancelable(false)
                .create()
                .show()

    }

    //跳入系统设置界面 设置权限
    fun showOpenAppSettingDialog(activity: Activity?, message: String, positiveListener: DialogInterface.OnClickListener, negativeListener: DialogInterface.OnClickListener) {
        if (activity == null || activity.isFinishing) return
        AlertDialog.Builder(activity, R.style.Theme_AppCompat_Light_Dialog_Alert)
                .setTitle("权限申请")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, positiveListener)
                .setNegativeButton(android.R.string.cancel, negativeListener)
                .setCancelable(false)
                .create()
                .show()
    }


//    private var mLoadingDialog: LoadingDialog? = null


    fun showLoadingDialog(activity: Activity) {
//        showLoadingDialog(activity, true, activity.getString(R.string.loading), false)
    }

    fun showLoadingDialog(activity: Activity, canDismiss: Boolean, progressTip: String, canBack: Boolean) {

//        if (mLoadingDialog?.isShowing == true) {
//            return
//        }
//        mLoadingDialog = LoadingDialog(activity)
//        mLoadingDialog!!.setCancelable(canDismiss)
//        mLoadingDialog!!.setCanceledOnTouchOutside(false)
//        mLoadingDialog?.setTip(progressTip)
//        mLoadingDialog?.setOnKeyListener { dialog, keyCode, _ ->
//            if (keyCode == KeyEvent.KEYCODE_BACK) {
//                if (canDismiss) {
//                    dialog.dismiss()
//                } else if (canBack) {
//                    activity.onBackPressed()
//                }
//            }
//            false
//        }
//
//        mLoadingDialog?.show()

    }

    fun dismissLoadingDialog() {
//        if (mLoadingDialog?.isShowing == true)
//            mLoadingDialog?.dismiss()
//        mLoadingDialog = null
    }


}
