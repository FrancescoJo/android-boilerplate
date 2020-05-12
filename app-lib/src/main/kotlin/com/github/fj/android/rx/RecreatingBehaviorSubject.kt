package com.github.fj.android.rx

import io.reactivex.subjects.BehaviorSubject
import kotlin.reflect.KProperty

/**
 * Begins from Jake Wharton's [RxRelay](https://github.com/JakeWharton/RxRelay).
 *
 * Observables are unable to use after onError happens once. However, reactive stream's such
 * contract is uncomfortable in some scenarios that waiting arbitrary inputs(user's touch,
 * mic input, data from remote peer, etc.).
 *
 * This utility helps to reuse BehaviorSubject after errors, without awkwardness.
 *
 * Internally, it recreates BehaviorSubject if previously created one is in terminal state.
 *
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
class RecreatingBehaviorSubject<T> {
    private lateinit var subject: BehaviorSubject<T>

    private var value: T? = null

    operator fun getValue(thisRef: Any?, property: KProperty<*>): BehaviorSubject<T> {
        if (!::subject.isInitialized) {
            subject = BehaviorSubject.create()
        }

        if (!subject.hasThrowable()) {
            this.value = subject.value
        }

        if (subject.isTerminated()) {
            subject = BehaviorSubject.create()
            if (value != null) {
                subject.onNext(value!!)
            }
        }

        return subject
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: BehaviorSubject<T>) {
        this.subject = value
        this.value = subject.value
    }
}
