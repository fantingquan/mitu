package com.mitu.android.utils;

import com.mitu.android.constant.Constants;
import com.mitu.android.data.local.SPManager;

/**
 * Life Is Better With DayDayCook
 * author: HuangQiang
 * created on: 2018/12/18 15:15.
 * description:切换地区
 */
public class IpHostUtils {

    public static String IP_MI_TAO = "https://datas.daydaycook.com.cn/product/";
    public static String IP_DDC_MT_SERVICE = "https://ddc-mt-service.daydaycook.com.cn/";
    public static String PAY_HOST = "https://pay.daydaycook.com.cn/";
    public static String WEB_HOST = "https://mobile.daydaycook.com.cn/";


    public enum Environment {
        dev,              //开发环境
        test,             //测试环境
        staging,          //预上线环境
        product,         //生产版本
    }


    //获取当前服务器
    public static Environment getHost() {
        String environment = SPManager.INSTANCE.getString(Constants.SP_LOCAL_IP, Environment.test.name());
        return Environment.valueOf(environment);
    }

    public static void changeApi(Environment apiEnvironment) {
        switch (apiEnvironment) {
            case dev:

                IP_MI_TAO = "https://datas.daydaycook.com.cn/dev/";
                IP_DDC_MT_SERVICE = "https://ddc-mt-service-d.daydaycook.com.cn/";
                PAY_HOST = "https://pay-t.daydaycook.com.cn/";
                WEB_HOST = "https://mobile-dev.daydaycook.com.cn/";

                break;

            case test:

                IP_MI_TAO = "https://datas.daydaycook.com.cn/test/";
                IP_DDC_MT_SERVICE = "https://ddc-mt-service-t.daydaycook.com.cn/";
                PAY_HOST = "https://pay-t.daydaycook.com.cn/";
                WEB_HOST = "https://mobile-test.daydaycook.com.cn/";

                break;

            case staging:

                IP_MI_TAO = "https://datas.daydaycook.com.cn/stag/";
                IP_DDC_MT_SERVICE = "https://ddc-mt-service-s.daydaycook.com.cn/";
                PAY_HOST = "https://pay-s.daydaycook.com.cn/";
                WEB_HOST = "https://mobile-staging.daydaycook.com.cn/";

                break;

            case product:

                IP_MI_TAO = "https://datas.daydaycook.com.cn/product/";
                IP_DDC_MT_SERVICE = "https://mt-service.daydaycook.com.cn/";
                PAY_HOST = "https://pay.daydaycook.com.cn/";
                WEB_HOST = "https://mobile.daydaycook.com.cn/";

                break;


            default:
                break;
        }
    }
}
