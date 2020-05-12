package com.github.fj.android.widget

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
import android.view.ViewGroup

fun ViewGroup.MarginLayoutParams.copyMarginsFrom(src: ViewGroup.MarginLayoutParams): ViewGroup.MarginLayoutParams {
    setMargins(src.leftMargin, src.topMargin, src.rightMargin, src.bottomMargin)
    return this
}
