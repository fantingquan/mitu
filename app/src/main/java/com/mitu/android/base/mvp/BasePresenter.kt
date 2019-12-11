package com.mitu.android.base.mvp

/**
 * Base class that implements the Presenter interface and provides a base implementation for
 * attachView() and detachView(). It also handles keeping a reference to the iView that
 * can be accessed from the children classes by calling getIView().
 */

//Kotlin 里类默认都是final的,如果声明的类允许被继承则需要使用open关键字来描述类
open class BasePresenter<T : IView> : IPresenter<T> {

    //setter方法
    //也可以写成一行(需要加分号),var view: T?=null;private set
    var iView: T? = null
        private set


    //函数默认也是final的,不能被override,要想重写父类函数,父类函数必须使用open定义

    override fun attachView(iView: T) {
        this.iView = iView
    }

    override fun detachView() {
        iView = null

    }

    //getter方法
    private val isViewAttached: Boolean
        get() = iView != null

    fun checkViewAttached() {
        if (!isViewAttached) throw MvpViewNotAttachedException()
    }

    //internal 修饰符是指成员的可见性是只在同一个模块(module)中才可见的
    private class MvpViewNotAttachedException internal constructor() : RuntimeException("Please call Presenter.attachView(MvpView) before" + " requesting data to the Presenter")

}

