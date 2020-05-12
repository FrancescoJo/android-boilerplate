package com.github.fj.android.awesomeapp.lifecycle

import android.os.Looper
import androidx.lifecycle.ViewModel
import com.github.fj.android.LibApplicationHolder
import com.github.fj.android.awesomeapp.BuildConfig
import com.github.fj.android.awesomeapp.CoreApplicationHolder
import com.github.fj.android.rx.onErrorPrinter
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.Subject
import timber.log.Timber

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
abstract class RxViewModel : ViewModel() {
    private var disposables = CompositeDisposable()

    protected fun <T> Single<T>.subscribeUntilCleared(onCallback: (T?, Throwable?) -> Unit) {
        subscribeUntilCleared(
            onSuccess = { onCallback(it, null) },
            onError = { onCallback(null, it) }
        )
    }

    /**
     * Subscribes until this ViewModel is cleared with using simple error printing consumer in
     * case of error.
     */
    protected fun <T> Single<T>.subscribeUntilCleared(onSuccess: (T) -> Unit) {
        subscribeUntilCleared(onSuccess, onErrorPrinter())
    }

    protected fun <T> Single<T>.subscribeUntilCleared(
        onSuccess: (T) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        disposables.add(this.subscribe { data, throwable ->
            if (throwable == null) {
                onSuccess(data)
            } else {
                onError(throwable)
            }
        })
    }

    protected fun <T> Observable<T>.subscribeUntilCleared(onCallback: (T?, Throwable?) -> Unit) {
        subscribeUntilCleared(
            onSuccess = { onCallback(it, null) },
            onError = { onCallback(null, it) }
        )
    }

    /**
     * Subscribes until this ViewModel is cleared with using simple error printing consumer in
     * case of error.
     */
    protected fun <T> Observable<T>.subscribeUntilCleared(onSuccess: (T) -> Unit) {
        subscribeUntilCleared(onSuccess, onErrorPrinter())
    }

    protected fun <T> Observable<T>.subscribeUntilCleared(
        onSuccess: (T) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        disposables.add(this.subscribe({ onSuccess(it) }, { onError(it) }))
    }

    protected fun <T> Single<List<T>>.relayTo(target: Subject<T>): Disposable =
        this.subscribe { data, throwable ->
            warnIfMainThread()
            if (throwable == null) {
                data.forEach {
                    target.onNext(it)
                }
            } else {
                target.onError(throwable)
            }
        }

    protected fun <T> Observable<T>.relayTo(target: Subject<T>): Disposable =
        this.subscribe({
            warnIfMainThread()
            target.onNext(it)
        }, {
            warnIfMainThread()
            target.onError(it)
        })

    protected fun Disposable.untilCleared() {
        disposables.add(this)
    }

    override fun onCleared() {
        disposables.dispose()
        disposables = CompositeDisposable()
        super.onCleared()
    }

    private fun warnIfMainThread() {
        if (!BuildConfig.DEBUG || CoreApplicationHolder.instance.isUnderInstrumentation) {
            return
        }

        if (Thread.currentThread() == Looper.getMainLooper().thread) {
            val errMsg = "Relaying source data stream to target subject on main thread " +
                    "is not recommended."
            val traces = Throwable().stackTrace
                .map {
                    val loggable = it.className.startsWith(LibApplicationHolder.instance.packageName)
                    return@map if (loggable) {
                        "at $it"
                    } else {
                        "..."
                    }
                }
                .distinct()
                .joinToString("\n")

            Timber.w("%s\n%s", errMsg, traces)
        }
    }
}
