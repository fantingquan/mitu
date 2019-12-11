package com.mitu.android.inject.component

import com.mitu.android.base.BaseActivity

import com.mitu.android.inject.PerActivity
import com.mitu.android.inject.module.ActivityModule
import dagger.Subcomponent

@PerActivity
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(baseActivity: BaseActivity)

}
