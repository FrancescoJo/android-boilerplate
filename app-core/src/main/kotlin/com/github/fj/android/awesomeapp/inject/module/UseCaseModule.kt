package com.github.fj.android.awesomeapp.inject.module

import com.github.fj.android.annotation.AllOpen
import com.github.fj.android.awesomeapp.CoreApplicationHolder
import com.github.fj.android.awesomeapp.repository.photo.PhotoRepository
import com.github.fj.android.awesomeapp.uc.photo.PhotoUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
@AllOpen
@Module
class UseCaseModule {
    init {
        val application = CoreApplicationHolder.instance

        if (!application.isUnderInstrumentation) {
            Repositories.init(application.baseApiUrl, application.coreLoggingLevel)
        }
    }

    @Provides
    @Singleton
    fun photoUseCase() = PhotoUseCase.create(Repositories.get(PhotoRepository::class))
}
