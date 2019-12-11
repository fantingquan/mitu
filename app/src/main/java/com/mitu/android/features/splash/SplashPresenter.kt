package com.mitu.android.features.splash


import com.mitu.android.data.remote.DataManager
import com.mitu.android.inject.ConfigPersistent
import javax.inject.Inject

/**
 * Life Is Better With DayDayCook
 * author: HuangQiang
 * created on: 2018/7/10 15:30.
 * description:
 */
@ConfigPersistent
class SplashPresenter @Inject constructor(private val dataManager: DataManager) : SplashContract.Presenter() {

}