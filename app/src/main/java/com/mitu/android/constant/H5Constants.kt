package com.mitu.android.constant

import com.mitu.android.utils.IpHostUtils

/**
 * author: tingquan.fan@daydaycook.com
 * created on: 2018/12/3 下午6:26
 * description: h5地址
 */
object H5Constants {
    const val index = "index.html"

    fun getH5(url: String): String {
        return IpHostUtils.WEB_HOST + url
    }

}