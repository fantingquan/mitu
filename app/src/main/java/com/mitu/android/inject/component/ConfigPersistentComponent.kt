package com.daydaycook.mitao.inject.component

import com.mitu.android.inject.ConfigPersistent
import com.mitu.android.inject.module.ActivityModule
import com.mitu.android.inject.module.FragmentModule
import com.mitu.android.inject.component.ActivityComponent
import com.mitu.android.inject.component.ApplicationComponent
import com.mitu.android.inject.component.FragmentComponent
import dagger.Component

/**
 * A dagger component that will live during the lifecycle of an Activity or Fragment but it won't
 * be destroy during configuration changes. Check [BaseActivity] and [BaseFragment] to
 * see how this components survives configuration changes.
 * Use the [ConfigPersistent] scope to annotate dependencies that need to survive
 * configuration changes (for example Presenters).
 */
@ConfigPersistent
@Component(dependencies = [ApplicationComponent::class])
interface ConfigPersistentComponent {

    fun activityComponent(activityModule: ActivityModule): ActivityComponent

    fun fragmentComponent(fragmentModule: FragmentModule): FragmentComponent

}
