package com.mitu.android.inject.module

import android.app.Application
import android.content.Context
import com.mitu.android.inject.ApplicationContext
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by HuangQiang on 18/4/26.
 */
@Module(includes = [ApiModule::class])
class ApplicationModule(private val mApplication: Application) {

    @Provides
    @Singleton
    fun provideApplication(): Application {
        return mApplication
    }

    @Provides
    @ApplicationContext
    fun provideContext(): Context {
        return mApplication
    }


}