package com.mitu.android.base

/**
 * Created by HuangQiang on 18/5/28.
 */
open class BaseModel<T> {
    var code: Int? = null
    var message: String? = null
    var data: T? = null
}
