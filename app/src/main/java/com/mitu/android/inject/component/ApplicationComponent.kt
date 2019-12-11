package com.mitu.android.inject.component

import android.app.Application
import android.content.Context
import com.mitu.android.data.remote.DataApi
import com.mitu.android.data.remote.DataManager
import com.mitu.android.inject.ApplicationContext
import com.mitu.android.inject.module.ApplicationModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by HuangQiang on 18/4/26.
 */

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    @ApplicationContext
    fun context(): Context

    fun application(): Application

    fun dataManager(): DataManager

    fun dataApi(): DataApi
}