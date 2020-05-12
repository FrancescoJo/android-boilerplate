package com.github.fj.android.rx

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun Completable.toAndroidAsync() =
    this.subscribeOnIoThread().observeOnMainThread()

fun Completable.onIoThread() =
    this.subscribeOnIoThread().observeOnIoThread()

fun Completable.subscribeOnIoThread(): Completable =
    subscribeOn(Schedulers.io())

fun Completable.observeOnMainThread(): Completable =
    observeOn(AndroidSchedulers.mainThread())

fun Completable.observeOnIoThread(): Completable =
    observeOn(Schedulers.io())
