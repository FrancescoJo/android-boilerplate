package com.github.fj.android.awesomeapp.inject.module

import com.github.fj.android.annotation.AllOpen
import com.github.fj.android.awesomeapp.core.photo.datasource.PhotoApiDataSource
import com.github.fj.android.awesomeapp.core.photo.datasource.PhotoApiMemDataSource
import com.github.fj.android.awesomeapp.core.photo.datasource.PhotoApiService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 05 - Jun - 2020
 */
@AllOpen
@Module(includes = [ApiServiceModule::class])
internal class DataSourceModule {
    @Provides
    @Singleton
    fun photoApiDataSource(apiSvc: PhotoApiService) = PhotoApiDataSource(apiSvc)

    @Provides
    @Singleton
    fun photoMemDataSource() = PhotoApiMemDataSource()
}
