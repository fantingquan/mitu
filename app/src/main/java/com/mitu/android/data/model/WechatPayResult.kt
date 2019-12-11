package com.mitu.android.data.model

/**
 * Life Is Better With DayDayCook
 * author: HuangQiang
 * created on: 2018/12/11 19:43.
 * description:
 */
data class WechatPayResult(var appId: String,
                           var partnerid: String,
                           var prepay_id: String,
                           var nonceStr: String,
                           var timeStamp: String,
                           var sign: String)