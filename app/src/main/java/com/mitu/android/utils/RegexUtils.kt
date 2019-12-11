package com.mitu.android.utils

import android.text.TextUtils
import java.util.regex.Pattern

/**
 * Life Is Better With DayDayCook
 * author: HuangQiang
 * created on: 2018/11/30 10:40.
 * description:各种正则表达式验证
 */

/**
 * 是否为手机号
 * @param mobileStr 为用户输入的手机号
 * @return
 */
fun isMobile(mobileStr: String): Boolean {
    if (TextUtils.isEmpty(mobileStr)) return false
    val p = Pattern.compile("^1\\d{10}$")
    val m = p.matcher(mobileStr)
    return m.matches()
}

/**
 * 验证输入的名字是否为“中文”或者是否包含“·”
 * @param nameStr 为用户输入的姓名
 * @return
 */
fun isName(nameStr: String): Boolean {
    return if (nameStr.contains("·") || nameStr.contains("•")) {
        nameStr.matches("^[\\u4e00-\\u9fa5]+[·•][\\u4e00-\\u9fa5]+$".toRegex())
    } else {
        nameStr.matches("^[\\u4e00-\\u9fa5]+$".toRegex())
    }
}


/**
 * 校验银行卡号
 * @param cardId String
 * @return Boolean
 */
fun isBankCard(cardId: String): Boolean {


    if (cardId.isEmpty() || cardId.length < 16||cardId.length>19) {
        return false
    }
    return true
//    val bit = getBankCardCheckCode(cardId
//            .substring(0, cardId.length - 1))
//    return if (bit == 'N') {
//        false
//    } else cardId[cardId.length - 1] == bit
}

/**
 * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
 *
 * @param nonCheckCodeCardId
 * @return
 */
fun getBankCardCheckCode(nonCheckCodeCardId: String?): Char {
    if (nonCheckCodeCardId == null
            || nonCheckCodeCardId.trim { it <= ' ' }.isEmpty()
            || !nonCheckCodeCardId.matches("\\d+".toRegex())) {
        // 如果传的不是数据返回N
        return 'N'
    }
    val chs = nonCheckCodeCardId.trim { it <= ' ' }.toCharArray()
    var luhmSum = 0
    var i = chs.size - 1
    var j = 0
    while (i >= 0) {
        var k = chs[i] - '0'
        if (j % 2 == 0) {
            k *= 2
            k = k / 10 + k % 10
        }
        luhmSum += k
        i--
        j++
    }
    return if (luhmSum % 10 == 0) '0' else (10 - luhmSum % 10 + '0'.toInt()).toChar()
}
