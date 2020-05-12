package com.github.fj.android.rx

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Single<T>.toAndroidAsync() =
    this.subscribeOnIoThread().observeOnMainThread()

fun <T> Single<T>.onIoThread() =
    this.subscribeOnIoThread().observeOnIoThread()

fun <T> Single<T>.subscribeOnIoThread(): Single<T> =
    subscribeOn(Schedulers.io())

fun <T> Single<T>.observeOnMainThread(): Single<T> =
    observeOn(AndroidSchedulers.mainThread())

fun <T> Single<T>.observeOnIoThread(): Single<T> =
    observeOn(Schedulers.io())

fun <T1, T2> Single<List<T1>>.flatMapInner(mapper: (T1) -> T2): Single<List<T2>> {
    return flattenAsObservable { it }.map { mapper(it) }.toList()
}
