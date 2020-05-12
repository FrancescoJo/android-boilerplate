package com.github.fj.android.rx

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Maybe<T>.toAndroidAsync() =
    this.subscribeOnIoThread().observeOnMainThread()

fun <T> Maybe<T>.onIoThread() =
    this.subscribeOnIoThread().observeOnIoThread()

fun <T> Maybe<T>.subscribeOnIoThread(): Maybe<T> =
    subscribeOn(Schedulers.io())

fun <T> Maybe<T>.observeOnMainThread(): Maybe<T> =
    observeOn(AndroidSchedulers.mainThread())

fun <T> Maybe<T>.observeOnIoThread(): Maybe<T> =
    observeOn(Schedulers.io())
