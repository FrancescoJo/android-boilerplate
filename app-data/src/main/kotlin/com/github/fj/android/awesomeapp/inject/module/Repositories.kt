package com.github.fj.android.awesomeapp.inject.module

import com.github.fj.android.annotation.LogLevel
import com.github.fj.android.awesomeapp.datasource.photo.PhotoApiDataSource
import com.github.fj.android.awesomeapp.datasource.photo.PhotoApiMemDataSource
import com.github.fj.android.awesomeapp.inject.SingletonFactory
import com.github.fj.android.awesomeapp.repository.Repository
import com.github.fj.android.awesomeapp.repository.photo.PhotoRepository
import com.github.fj.android.awesomeapp.repository.photo.PhotoRepositoryImpl
import kotlin.reflect.KClass

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
object Repositories : SingletonFactory<Repository>() {
    /**
     * This method must be called before accessing any instances via [get].
     */
    fun init(baseApiUrl: String, @LogLevel logLevel: Int) {
        super.init()
        DataSources.init(baseApiUrl, logLevel)
    }

    override fun createInstanceOf(klass: KClass<out Repository>): Repository? = when (klass) {
        PhotoRepository::class -> PhotoRepositoryImpl(
            DataSources.get(PhotoApiDataSource::class),
            DataSources.get(PhotoApiMemDataSource::class)
        )
        else -> null
    }
}
