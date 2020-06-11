package com.github.fj.android.awesomeapp.inject.module

import com.github.fj.android.annotation.AllOpen
import com.github.fj.android.awesomeapp.core.photo.datasource.PhotoApiService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import kotlin.reflect.KClass

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 05 - Jun - 2020
 */
@AllOpen
@Module
internal class ApiServiceModule {
    protected fun <T : Any> createApiSvc(typeOfT: KClass<T>): T =
        RetrofitApiServiceFactory.get(typeOfT)

    @Provides
    @Singleton
    fun photoApiService(): PhotoApiService = createApiSvc(PhotoApiService::class)
}
