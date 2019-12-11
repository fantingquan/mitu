package com.mitu.android.event

import com.tencent.mm.opensdk.modelbase.BaseResp


/**
 * Created by HuangQiang on 18/5/11.
 * 微信支付结果
 */
class WechatPayResultEvent(data: BaseResp) : AppEvent<BaseResp>(data)