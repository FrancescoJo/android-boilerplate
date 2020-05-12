package com.github.fj.android.net

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
@Suppress("MagicNumber")
fun Int.is2XXSuccess(): Boolean = this in 200..299

@Suppress("MagicNumber")
fun Int.is3XXRedirection(): Boolean = this in 300..399

@Suppress("MagicNumber")
fun Int.is4XXClientError(): Boolean = this in 400..499

@Suppress("MagicNumber")
fun Int.is5XXServerError(): Boolean = this in 500..599

fun Int.isError(): Boolean = is4XXClientError() || is5XXServerError()
