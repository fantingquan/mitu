package com.mitu.android.base.mvp

/**
 * Every presenter in the app must either implement this interface or extend BasePresenter
 * indicating the IView type that wants to be attached with.
 */
interface IPresenter<in V : IView> {

    fun attachView(iView: V)

    fun detachView()
}
