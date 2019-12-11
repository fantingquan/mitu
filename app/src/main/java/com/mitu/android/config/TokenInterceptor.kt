package com.mitu.android.config


import android.text.TextUtils
import com.mitu.android.constant.CodeConstants

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import org.json.JSONObject

/**
 * Life Is Better With DayDayCook
 * author: HuangQiang
 * created on: 2018/12/10 17:01.
 * description: 应用拦截器  addInterceptor:设置应用拦截器，主要用于设置公共参数，头信息，日志拦截等； addNetworkInterceptor：设置网络拦截器，主要用于重试或重写
 */
class TokenInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)


        var body = response.body()

        if (body != null) {
            val mediaType = body.contentType()
            if (mediaType != null) {

                val resp = body.string()

                try {
                    //根据和服务端的约定判断token过期
                    if (isTokenExpired(resp)) {
//                        LogUtils.e("TokenInterceptor", "token过期 response = " + String.format(Locale.CHINA, "%s  返回 %s",
//                                request.url(), resp))
//                        AccountHelper.loginOut()
//                        val intent = Intent(Utils.getApp(), LoginActivity::class.java)
//                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
//                        Utils.getApp().startActivity(intent)
                    }
                } catch (e: Throwable) {
                    e.printStackTrace()
                }

                body = ResponseBody.create(mediaType, resp)
                return response.newBuilder().body(body).build()

            }
        }


        return response
    }


    /**
     * 根据Response，判断Token是否失效
     *
     * @param result
     * @return
     */
    private fun isTokenExpired(result: String?): Boolean {
        try {
            if (result != null && !TextUtils.isEmpty(result)) {
                val jsonObject = JSONObject(result)
                if (jsonObject.has("code")) {
                    val code = jsonObject.getString("code")
                    return CodeConstants.NET_EXPIRED.toString() == code
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return false
    }


}
