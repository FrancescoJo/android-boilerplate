package com.github.fj.android.awesomeapp.datasource.photo

import com.github.fj.android.annotation.AllOpen
import com.github.fj.android.awesomeapp.datasource.ApiDataSource
import com.github.fj.android.awesomeapp.dto.photo.ImageDetailDto
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

@AllOpen
internal class PhotoApiDataSource(apiSvc: PhotoApiService) : ApiDataSource(),
    PhotoApiService by apiSvc
