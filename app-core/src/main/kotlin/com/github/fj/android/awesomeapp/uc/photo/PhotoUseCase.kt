package com.github.fj.android.awesomeapp.uc.photo

import com.github.fj.android.awesomeapp.model.photo.ImageDetail
import com.github.fj.android.awesomeapp.repository.photo.PhotoRepository
import com.github.fj.android.awesomeapp.uc.UseCase
import io.reactivex.Single

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
interface PhotoUseCase : UseCase {
    fun load(strict: Boolean = false, forceRefresh: Boolean = true): Single<List<ImageDetail>>

    companion object {
        fun create(photoRepository: PhotoRepository): PhotoUseCase =
            PhotoUseCaseImpl(photoRepository)
    }
}
