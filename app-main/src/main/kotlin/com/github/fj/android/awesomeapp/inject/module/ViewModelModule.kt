package com.github.fj.android.awesomeapp.inject.module

import androidx.lifecycle.ViewModel
import com.github.fj.android.awesomeapp.inject.ViewModelKey
import com.github.fj.android.awesomeapp.ui.MainActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
@Module
@Suppress("Unused") // All Activity declarations are used by Dagger2 internally
abstract class ViewModelModule {
    // I miss Spring's @AliasFor annotation for this
    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun mainActivityVm(instance: MainActivityViewModel): ViewModel
}
