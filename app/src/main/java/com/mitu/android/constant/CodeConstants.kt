package com.mitu.android.constant

/**
 * author: tingquan.fan@daydaycook.com
 * created on: 2018/12/7 下午2:49
 * description: 所有公共Code在此定义
 */
object CodeConstants {
    /**
     * 网络状态码
     */
    const val NET_SUCCESS = 1  //请求成功
    const val NET_FAIL = 0 //请求返回失败
    const val NET_PARAMETER_ERROR = 2 //请求参数错误
    const val NET_EXPIRED = 3 //未登录

    /**
     * 会员等级
     * (0:普通用户,1:VIP,2:销售总监,3:销售总经理)
     */
    const val USER_LEVEL_NORMAL = 0
    const val USER_LEVEL_VIP = 1
    const val USER_LEVEL_SALES_DIRECTOR = 2
    const val USER_LEVEL_SALES_MANAGER = 3


    const val WRONG_ID = "-1"  //无效的model

}