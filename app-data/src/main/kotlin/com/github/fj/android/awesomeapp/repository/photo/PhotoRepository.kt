package com.github.fj.android.awesomeapp.repository.photo

import com.github.fj.android.awesomeapp.dto.photo.ImageDetailDto
import com.github.fj.android.awesomeapp.repository.Repository
import io.reactivex.Single

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
interface PhotoRepository : Repository {
    /**
     * @return up-to-date data from server if [forceRefresh] = `true`; cached data from
     * otherwise. Data expiration follows general cache policy.
     */
    fun loadList(forceRefresh: Boolean = true): Single<List<ImageDetailDto>>
}
