package com.github.fj.android.awesomeapp.inject.module

import com.github.fj.android.annotation.LogLevel
import com.github.fj.android.awesomeapp.datasource.DataSource
import com.github.fj.android.awesomeapp.datasource.photo.PhotoApiDataSource
import com.github.fj.android.awesomeapp.datasource.photo.PhotoApiMemDataSource
import com.github.fj.android.awesomeapp.datasource.photo.PhotoApiService
import com.github.fj.android.awesomeapp.inject.SingletonFactory
import kotlin.reflect.KClass

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
internal object DataSources : SingletonFactory<DataSource>() {
    fun init(baseApiUrl: String, @LogLevel logLevel: Int) {
        super.init()
        RetrofitServices.init(baseApiUrl, logLevel)
    }

    override fun createInstanceOf(klass: KClass<out DataSource>): DataSource? = when (klass) {
        PhotoApiDataSource::class -> PhotoApiDataSource(RetrofitServices.get(PhotoApiService::class))
        PhotoApiMemDataSource::class -> PhotoApiMemDataSource()
        else -> null
    }
}
