package com.mitu.android.features.splash

import com.mitu.android.base.mvp.BaseContract
import com.mitu.android.base.mvp.BasePresenter
import com.mitu.android.base.mvp.IView


/**
 * Life Is Better With DayDayCook
 * author: HuangQiang
 * created on: 2018/7/10 15:27.
 * description: Splash mvp contract
 */
class SplashContract : BaseContract() {
    interface View : IView

    abstract class Presenter : BasePresenter<View>()
}