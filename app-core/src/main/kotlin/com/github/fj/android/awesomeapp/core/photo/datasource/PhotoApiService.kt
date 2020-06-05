package com.github.fj.android.awesomeapp.core.photo.datasource

import com.github.fj.android.awesomeapp.core.photo.dto.ImageDetailDto
import io.reactivex.Single
import retrofit2.http.GET

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
internal interface PhotoApiService {
    @GET("/v2/list")
    fun getAll(): Single<List<ImageDetailDto>>
}
