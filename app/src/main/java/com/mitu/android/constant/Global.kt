package com.mitu.android.constant

import android.text.TextUtils

/**
 * Life Is Better With DayDayCook
 * author: HuangQiang
 * created on: 2018/7/10 16:32.
 * description:
 */
object Global {


    /**
     * h5版本号，给h5刷新用
     */
    private var h5Version: String = ""

    fun setH5Version(h5Version: String) {
        Global.h5Version = h5Version
    }

    fun getH5Version(): String {
        if (TextUtils.isEmpty(h5Version)) {
            h5Version = System.currentTimeMillis().toString() + ""
        }
        return h5Version
    }


}