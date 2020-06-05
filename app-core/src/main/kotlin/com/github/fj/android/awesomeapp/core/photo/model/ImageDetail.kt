package com.github.fj.android.awesomeapp.core.photo.model

import com.github.fj.android.awesomeapp.CoreApplicationHolder
import com.github.fj.android.awesomeapp.core.photo.dto.ImageDetailDto

/**
 * Domain model of a detailed information of an image.
 *
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
interface ImageDetail {
    val id: String

    val author: String

    val width: Int

    val height: Int

    val url: String

    val downloadUrl: String

    fun thumbnailUrl(width: Int, height: Int): String {
        return CoreApplicationHolder.instance.baseApiUrl + "/id/$id/$width/$height"
    }

    companion object {
        @Suppress("LongParameterList")
        fun create(
            id: String,
            author: String,
            width: Int,
            height: Int,
            url: String,
            downloadUrl: String
        ): ImageDetail =
            ImageDetailImpl(
                id = id,
                author = author,
                width = width,
                height = height,
                url = url,
                downloadUrl = downloadUrl
            )

        internal fun from(dto: ImageDetailDto): ImageDetail =
            create(
                id = dto.id,
                author = dto.author,
                width = dto.width,
                height = dto.height,
                url = dto.url,
                downloadUrl = dto.downloadUrl
            )
    }
}
