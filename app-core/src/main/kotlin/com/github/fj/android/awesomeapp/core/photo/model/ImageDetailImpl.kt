package com.github.fj.android.awesomeapp.core.photo.model

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
) : ImageDetail
