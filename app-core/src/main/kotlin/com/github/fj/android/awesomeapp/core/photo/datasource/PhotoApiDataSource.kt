package com.github.fj.android.awesomeapp.core.photo.datasource

import com.github.fj.android.annotation.AllOpen
import com.github.fj.android.awesomeapp.core.ApiDataSource
import com.github.fj.android.awesomeapp.core.photo.dto.ImageDetailDto
import io.reactivex.Single

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
@AllOpen
internal class PhotoApiDataSource : ApiDataSource<PhotoApiService>(PhotoApiService::class) {
    fun getAll(): Single<List<ImageDetailDto>> = getService().getAll()
}
