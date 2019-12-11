package com.mitu.android.utils

import java.math.BigDecimal

object NumberFormatUtil {

    fun doubleToString(d: Double): String {
        //        return new DecimalFormat("##.##").format(d);

        //保留2位并且属于四舍五入类型，当然这里的四舍五入没有任何意义，可以选择其他类型。
        return BigDecimal(d).setScale(2, BigDecimal.ROUND_HALF_UP).toString()
    }

    /**
     *保留两位小数
     */
    fun doubleToString2(d: Double?): String {
        if (d == null) return "0.00"
        return try {
            String.format("%.2f", d)
        } catch (throwable: Throwable) {
            "0.00"
        }
    }

    /**
     *保留两位小数并格式化*w
     */
    fun doubleBigToString2(d: Double?, level: Int?): String {
//        val lev = level ?: CodeConstants.USER_LEVEL_NORMAL
//        return if (lev > CodeConstants.USER_LEVEL_VIP) {
//            "*****"
//        } else {
//            if (d == null) return "0.00"
//            if (d > 100000000) {
//                return String.format("%.1fW", d / 10000)
//            } else if (d > 10000) {
//                return String.format("%.2fW", d / 10000)
//            }
//            return String.format("%.2f", d)
//        }
        if (d == null) return "0.00"
        if (d > 100000000) {
            return String.format("%.1fW", d / 10000)
        } else if (d > 10000) {
            return String.format("%.2fW", d / 10000)
        }
        return String.format("%.2f", d)

    }

    /**
     * @param mobile 手机号
     */
    fun phoneToString(mobile: String?): String {
        if (mobile.isNullOrEmpty()) return ""
        if (mobile?.length ?: 0 < 8) return mobile!!
        return mobile?.substring(0, 3) + "****" + mobile?.substring(7, mobile.length)

    }




}
