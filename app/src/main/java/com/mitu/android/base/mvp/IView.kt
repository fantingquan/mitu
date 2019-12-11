package com.mitu.android.base.mvp

/**
 * Base interface that any class that wants to act as a View in the MVP (Model View Presenter)
 * pattern must implement. Generally this interface will be extended by a more specific interface
 * that then usually will be implemented by an Activity or Fragment.
 */
interface IView {
    fun showProgress(boolean: Boolean)
    fun showSnackBar(msg: String?, resId: Int)
    fun showPageState(state:Int)


}
