package com.github.fj.android.awesomeapp.core.photo.repository

import com.github.fj.android.awesomeapp.core.Repository
import com.github.fj.android.awesomeapp.core.photo.dto.ImageDetailDto
import io.reactivex.Single

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
internal interface PhotoRepository : Repository {
    /**
     * @return up-to-date data from server if [forceRefresh] = `true`; cached data from
     * otherwise. Data expiration follows general cache policy.
     */
    fun loadList(forceRefresh: Boolean = true): Single<List<ImageDetailDto>>
}
