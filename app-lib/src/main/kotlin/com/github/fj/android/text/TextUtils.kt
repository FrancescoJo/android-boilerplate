package com.github.fj.android.text

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
import android.os.Build
import android.text.Html
import android.text.Spanned

fun String.toHtml(): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, 0)
    } else {
        @Suppress("DEPRECATION")
        Html.fromHtml(this)
    }
}
