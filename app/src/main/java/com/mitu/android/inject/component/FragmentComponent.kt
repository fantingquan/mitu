package com.mitu.android.inject.component

import com.mitu.android.base.BaseFragment

import com.mitu.android.inject.PerFragment
import com.mitu.android.inject.module.FragmentModule
import dagger.Subcomponent

/**
 * This component inject dependencies to all Fragments across the application
 */
@PerFragment
@Subcomponent(modules = [FragmentModule::class])
interface FragmentComponent {
    fun inject(baseFragment: BaseFragment)

}