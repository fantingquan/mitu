package com.mitu.android.constant

import com.mitu.android.utils.IpHostUtils.*


/**
 * Created by HuangQiang on 18/4/27.
 * 只存放Api请求地址
 */

/**
 *
 */
object ApiConstants {

    /** ---------------IP_DDC_MT_SERVICE start --------------*/
    fun getUrlMiTao(url: String): String {
        return IP_MI_TAO + url
    }
    /** ---------------IP_DDC_MT_SERVICE EDN --------------*/


    /** ---------------IP_DDC_MT_SERVICE start --------------*/
    fun getUrlMiTaoService(url: String): String {
        return IP_DDC_MT_SERVICE + url
    }
    /** ---------------IP_DDC_MT_SERVICE EDN --------------*/


    /** ---------------PAY_HOST start --------------*/
    fun getUrlPay(url: String): String {
        return PAY_HOST + url
    }
    var onlinePay = "Pay/pay"//在线支付
    /** --------------- PAY_HOST end --------------*/


}