package com.github.fj.android.rx

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
import android.util.Log
import com.github.fj.android.annotation.LogLevel
import io.reactivex.functions.Consumer
import io.reactivex.subjects.Subject
import timber.log.Timber

fun Subject<*>.isTerminated(): Boolean {
    return this.hasThrowable() || this.hasComplete()
}

fun onErrorPrinter(@LogLevel level: Int = Log.ERROR): (Throwable) -> Unit = { t ->
    Timber.log(level, t, "")
}

fun errorPrintingConsumer(@LogLevel level: Int = Log.ERROR) = Consumer<Throwable> { t ->
    Timber.log(level, t, "")
}
