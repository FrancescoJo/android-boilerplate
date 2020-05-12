package com.github.fj.android.awesomeapp.inject.component

import com.github.fj.android.awesomeapp.inject.module.ActivityModule
import com.github.fj.android.awesomeapp.inject.module.FactoryModule
import com.github.fj.android.awesomeapp.inject.module.UseCaseModule
import com.github.fj.android.awesomeapp.inject.module.ViewModelModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class,
        UseCaseModule::class,
        FactoryModule::class,
        ActivityModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent : AndroidInjector<DaggerApplication>
