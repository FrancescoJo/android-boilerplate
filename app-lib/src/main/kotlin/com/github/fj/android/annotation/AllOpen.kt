package com.github.fj.android.annotation

/**
 * Marks that annotated class itself and its members are open to outside world.
 *
 * To make this annotation work, you must apply [Kotlin AllOpen compiler plugin](https://kotlinlang.org/docs/reference/compiler-plugins.html#all-open-compiler-plugin)
 * and configure this annotation in your build.gradle file.
 *
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
@Retention(AnnotationRetention.SOURCE)
annotation class AllOpen
