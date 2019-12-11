package com.mitu.android.constant

import android.os.Environment

/**
 * Created by HuangQiang on 18/5/7.
 */

object Constants {
    val SDCARD_ROOT: String = Environment.getExternalStorageDirectory().absolutePath + "/mitao"
    val SDCARD_IMG_ROOT = "$SDCARD_ROOT/img"
    val SDCARD_APK_ROOT = "$SDCARD_ROOT/apk"


    //SharedPreferences 的常量
    const val SP_ACCOUNT = "SP_ACCOUNT"
    const val SP_LOCAL_IP = "SP_LOCAL_IP"
    const val SP_IS_APP_UPDATE = "sp_is_app_update"//是否提示过版本更新
    const val SP_APP_VERSION = "SP_APP_VERSION"//版本更新版本号


    //微信AppId
    const val WECHAT_APP_ID = "wx761a59e357c8d9ed"


}
