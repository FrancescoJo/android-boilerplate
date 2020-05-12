package com.github.fj.android.util

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
fun <T> MutableList<T>.addAll(vararg args: T) {
    args.forEach {
        add(it)
    }
}

fun <T> MutableSet<T>.addAll(vararg args: T) {
    args.forEach {
        add(it)
    }
}
