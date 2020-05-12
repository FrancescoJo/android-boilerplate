package com.github.fj.android.awesomeapp.inject.module

import androidx.lifecycle.ViewModelProvider
import com.github.fj.android.awesomeapp.lifecycle.ViewModelFactory
import dagger.Binds
import dagger.Module

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
@Module
@Suppress("Unused") // All Activity declarations are used by Dagger2 internally
abstract class FactoryModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
