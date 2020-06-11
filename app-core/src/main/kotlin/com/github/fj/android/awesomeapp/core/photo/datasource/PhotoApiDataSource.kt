package com.github.fj.android.awesomeapp.core.photo.datasource

import com.github.fj.android.annotation.AllOpen
import com.github.fj.android.awesomeapp.core.ApiDataSource

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
@AllOpen
internal class PhotoApiDataSource(private val apiSvc: PhotoApiService) : ApiDataSource(),
    PhotoApiService by apiSvc
