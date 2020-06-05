package com.github.fj.android.awesomeapp.inject.module

import com.github.fj.android.annotation.AllOpen
import com.github.fj.android.awesomeapp.core.photo.repository.PhotoRepository
import com.github.fj.android.awesomeapp.core.photo.usecase.PhotoUseCase
import com.github.fj.android.awesomeapp.core.photo.usecase.PhotoUseCaseImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
@AllOpen
@Module(includes = [RepositoryModule::class])
class UseCaseModule {
    @Provides
    @Singleton
    internal fun photoUseCase(photoRepo: PhotoRepository): PhotoUseCase =
        PhotoUseCaseImpl(photoRepo)
}
