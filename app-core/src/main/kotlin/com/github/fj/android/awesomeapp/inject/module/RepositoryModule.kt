package com.github.fj.android.awesomeapp.inject.module

import com.github.fj.android.annotation.AllOpen
import com.github.fj.android.awesomeapp.core.photo.datasource.PhotoApiDataSource
import com.github.fj.android.awesomeapp.core.photo.datasource.PhotoApiMemDataSource
import com.github.fj.android.awesomeapp.core.photo.repository.PhotoRepository
import com.github.fj.android.awesomeapp.core.photo.repository.PhotoRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 05 - Jun - 2020
 */
@AllOpen
@Module(includes = [DataSourceModule::class])
internal class RepositoryModule {
    @Provides
    @Singleton
    fun photoRepository(
        apiDataSrc: PhotoApiDataSource,
        memCachedDataSrc: PhotoApiMemDataSource
    ): PhotoRepository = PhotoRepositoryImpl(apiDataSrc, memCachedDataSrc)
}
