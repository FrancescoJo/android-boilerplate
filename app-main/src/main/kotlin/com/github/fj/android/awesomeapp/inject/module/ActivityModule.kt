package com.github.fj.android.awesomeapp.inject.module

import com.github.fj.android.awesomeapp.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.DaggerAppCompatActivity

/**
 * Activity definitions for [DaggerAppCompatActivity]. All classes defined here must inherit
 * [DaggerAppCompatActivity] to utilise automatic injection facility.
 *
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
@Module
@Suppress("Unused") // All Activity declarations are used by Dagger2 internally
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity
}
