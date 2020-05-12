package com.github.fj.android.awesomeapp.model.photo

import com.github.fj.android.awesomeapp.dto.photo.ImageDetailDto

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 15 - May - 2020
 */
internal data class ImageDetailImpl(
    override val id: String,
    override val author: String,
    override val width: Int,
    override val height: Int,
    override val url: String,
    override val downloadUrl: String
) : ImageDetail {
    companion object {
        fun from(dto: ImageDetailDto): ImageDetail = ImageDetailImpl(
            id = dto.id,
            author = dto.author,
            width = dto.width,
            height = dto.height,
            url = dto.url,
            downloadUrl = dto.downloadUrl
        )
    }
}
