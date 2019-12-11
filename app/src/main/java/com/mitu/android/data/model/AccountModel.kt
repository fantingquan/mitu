package com.mitu.android.data.model

import java.io.Serializable

/**
 * Life Is Better With DayDayCook
 * author: HuangQiang
 * created on: 2018/12/1 17:05.
 * description:
 */
data class AccountModel(
        var userId:Int,
        var mobile: String,
        var nickName: String,
        var token: String,
        var header: String,
        var birthday: String,
        var gender: String,//1男2女
        var cardNo: String,
        var cardName: String,
        var inviteCode: String,//邀请码
        var level: Int,//用户等级(0:普通用户,1:VIP,2:销售总监,3:销售总经理)
        var inviter: String) : Serializable


