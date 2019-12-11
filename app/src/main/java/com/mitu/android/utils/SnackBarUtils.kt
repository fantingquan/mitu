package com.mitu.android.utils

import android.app.Activity
import com.mitu.android.R

/**
 * Life Is Better With DayDayCook
 * author: HuangQiang
 * created on: 2018/11/29 18:15.
 * description:
 */
object SnackBarUtils {

    /**
     * 成功的SnackBar
     * @param activity Activity
     * @param message String?
     */
    fun showSuccessSnackBar(activity: Activity, message: String?) {
        showSnackBar(activity, message, R.mipmap.toast_success)
    }

    /**
     * 错误提示的SnackBar
     * @param activity Activity
     * @param message String?
     */
    fun showWarnSnackBar(activity: Activity, message: String?) {
        showSnackBar(activity, message, R.mipmap.toast_warn)
    }

    /**
     * 显示在顶部的Snackbar，背景红色渐变
     * @param resId Int 图片id
     */
    fun showSnackBar(activity: Activity, message: String?, resId: Int) {
//        TSnackbar.make(activity.window.decorView, message
//                ?: activity.getString(R.string.handle_fail), TSnackbar.LENGTH_LONG)
//                .setPreDefinedStyle(TSnackbar.STYLE_NO)
//                .setFadeOrTranslateStyle(TSnackbar.STYLE_TRANSLATE)
//                .isBelowStatusBar(true)
//                .setBackgroundResource(R.drawable.drawable_snackbar_bg)
//                .setMessageTextColor(ContextCompat.getColor(activity, R.color.white))
//                .setMessageTextDrawableLeft(ContextCompat.getDrawable(activity, resId))
//                .show()
    }
}