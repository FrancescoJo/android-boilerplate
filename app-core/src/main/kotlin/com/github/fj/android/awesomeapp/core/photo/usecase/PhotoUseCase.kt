package com.github.fj.android.awesomeapp.core.photo.usecase

import com.github.fj.android.awesomeapp.core.UseCase
import com.github.fj.android.awesomeapp.core.photo.model.ImageDetail
import io.reactivex.Single

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
interface PhotoUseCase : UseCase {
    fun load(strict: Boolean = false, forceRefresh: Boolean = true): Single<List<ImageDetail>>
}
