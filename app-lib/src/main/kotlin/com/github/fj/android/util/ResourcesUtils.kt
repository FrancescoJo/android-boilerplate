package com.github.fj.android.util

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
import androidx.annotation.DimenRes
import androidx.annotation.StringRes
import com.github.fj.android.LibApplicationHolder

private fun getContext() = LibApplicationHolder.instance

fun getString(@StringRes resId: Int): String = getContext().getString(resId)

fun getString(@StringRes resId: Int, vararg args: Any): String =
    /*
     * Necessary evil
     * https://discuss.kotlinlang.org/t/hidden-allocations-when-using-vararg-and-spread-operator/1640
     */
    @Suppress("SpreadOperator")
    getContext().getString(resId, *args)

fun getDimen(@DimenRes resId: Int) =
    getContext().resources.getDimension(resId)
