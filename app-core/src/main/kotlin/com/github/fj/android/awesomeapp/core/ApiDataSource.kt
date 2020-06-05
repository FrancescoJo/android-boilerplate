package com.github.fj.android.awesomeapp.core

import com.github.fj.android.awesomeapp.inject.module.RetrofitApiServiceFactory
import kotlin.reflect.KClass

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
internal abstract class ApiDataSource<T : Any>(private val typeOfT: KClass<T>) : DataSource {
    protected fun getService(): T = RetrofitApiServiceFactory.get(typeOfT)
}
