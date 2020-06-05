package com.github.fj.android.awesomeapp.core.photo.repository

import com.github.fj.android.awesomeapp.core.photo.datasource.PhotoApiDataSource
import com.github.fj.android.awesomeapp.core.photo.datasource.PhotoApiMemDataSource
import com.github.fj.android.awesomeapp.core.photo.dto.ImageDetailDto
import com.github.fj.android.awesomeapp.exception.CachedDataException
import io.reactivex.Single
import timber.log.Timber

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
internal class PhotoRepositoryImpl(
    private val apiDataSrc: PhotoApiDataSource,
    private val memCachedDataSrc: PhotoApiMemDataSource
) : PhotoRepository {
    override fun loadList(forceRefresh: Boolean): Single<List<ImageDetailDto>> = if (forceRefresh) {
        loadListFromApi()
    } else {
        Single.defer {
            @Suppress("TooGenericExceptionCaught") // For exception branching
            return@defer try {
                Single.just(memCachedDataSrc.getAll())
            } catch (e: RuntimeException) {
                if (e is CachedDataException) {
                    Timber.v(e)
                } else {
                    Timber.e(e)
                }
                loadListFromApi()
            }
        }
    }

    private fun loadListFromApi() = apiDataSrc.getAll().doOnSuccess { memCachedDataSrc.putAll(it) }
}
