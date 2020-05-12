package com.github.fj.android.rx

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Observable<T>.toAndroidAsync() =
    this.subscribeOnIoThread().observeOnMainThread()

fun <T> Observable<T>.onIoThread() =
    this.subscribeOnIoThread().observeOnIoThread()

fun <T> Observable<T>.subscribeOnIoThread(): Observable<T> =
    subscribeOn(Schedulers.io())

fun <T> Observable<T>.observeOnMainThread(): Observable<T> =
    observeOn(AndroidSchedulers.mainThread())

fun <T> Observable<T>.observeOnIoThread(): Observable<T> =
    observeOn(Schedulers.io())
