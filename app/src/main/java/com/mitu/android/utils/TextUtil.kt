package com.mitu.android.utils

import android.graphics.Paint
import android.graphics.Typeface
import android.text.TextUtils
import android.widget.TextView

/**
 * author: tingquan.fan@daydaycook.com
 * created on: 2018/12/6 下午4:14
 * description: text相关的工具类
 */
object TextUtil {
    /**
     * 字体粗细设置
     * @param textView
     * @param strokeWidth  正常1.0f 加粗1.2f
     */
    fun setLightBold(textView: TextView, strokeWidth: Float) {
        if (strokeWidth == 1.0f) {
            textView.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
//            textView.typeface = Typeface.create("sans-serif", Typeface.NORMAL)
        } else {
            textView.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
//            textView.typeface = Typeface.create("sans-serif", Typeface.NORMAL)
//            textView.paint.style = Paint.Style.FILL_AND_STROKE
//            textView.paint.strokeWidth = strokeWidth
        }
    }

    /**
     * 字体粗细设置
     */
    fun setMedium(textView: TextView?) {

        textView?.typeface = Typeface.create("sans-serif", Typeface.NORMAL)
        textView?.paint?.style = Paint.Style.FILL_AND_STROKE
        textView?.paint?.strokeWidth = 1.1f
    }

    /**
     * 计算string字符长度,中文两个字符，英文1个字符
     * @param str String
     * @return Int
     */

    fun handleText(str: String): Int {
        if (TextUtils.isEmpty(str)) {
            return 0
        }
        var count = 0
        str.forEach {
            count += if(it < 128.toChar()){
                1
            }else{
                2
            }
        }
        return count
    }
}