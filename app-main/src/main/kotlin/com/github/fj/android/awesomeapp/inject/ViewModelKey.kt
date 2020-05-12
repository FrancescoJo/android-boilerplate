package com.github.fj.android.awesomeapp.inject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.MapKey
import javax.inject.Singleton
import kotlin.reflect.KClass

/**
 * A custom scope to declare ViewModel classes those are created by [ViewModelProvider.Factory].
 *
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MapKey
@Singleton
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)
