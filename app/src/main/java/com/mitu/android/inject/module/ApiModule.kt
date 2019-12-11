package com.mitu.android.inject.module

import com.mitu.android.data.remote.DataApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by HuangQiang on 18/4/28.
 */

@Module(includes = [NetWorkModule::class])
class ApiModule {

    @Provides
    @Singleton
    internal fun provideDataApi(retrofit: Retrofit): DataApi =
            retrofit.create(DataApi::class.java)
}