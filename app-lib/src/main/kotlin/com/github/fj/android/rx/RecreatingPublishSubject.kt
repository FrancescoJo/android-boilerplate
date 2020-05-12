package com.github.fj.android.rx

import io.reactivex.subjects.PublishSubject
import kotlin.reflect.KProperty

/**
 * Begins from Jake Wharton's [RxRelay](https://github.com/JakeWharton/RxRelay).
 *
 * Observables are unable to use after onError happens once. However, reactive stream's such
 * contract is uncomfortable in some scenarios that waiting arbitrary inputs(user's touch,
 * mic input, data from remote peer, etc.).
 *
 * This utility helps to reuse PublishSubject after errors, without awkwardness.
 *
 * Internally, it recreates PublishSubject if previously created one is in terminal state.
 *
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
class RecreatingPublishSubject<T> {
    private lateinit var subject: PublishSubject<T>

    operator fun getValue(thisRef: Any?, property: KProperty<*>): PublishSubject<T> {
        if (!::subject.isInitialized) {
            subject = PublishSubject.create()
        }

        if (subject.isTerminated()) {
            subject = PublishSubject.create()
        }

        return subject
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: PublishSubject<T>) {
        this.subject = value
    }
}
