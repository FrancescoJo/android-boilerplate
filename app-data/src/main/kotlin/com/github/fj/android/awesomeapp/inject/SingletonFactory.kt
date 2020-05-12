package com.github.fj.android.awesomeapp.inject

import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass

/**
 * A simple singleton manager created by myself. This class and its successors are not required
 * if we declare a Dagger component which has certain scope that does not exposed to further
 * modules.
 *
 * Use this facility until we solve following problem:
 * ```
 * error: cannot access PhotoRepository
 * Consult the following stack trace for details.
 *
 * com.sun.tools.javac.code.Symbol$CompletionFailure:
 *   class file for com.github.fj.android.awesomeapp.repository.photo.PhotoRepository not found
 * ```
 *
 * while we hide visibility of `app-data` against `app-main`.
 *
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
abstract class SingletonFactory<T : Any> {
    private val instances = ConcurrentHashMap<KClass<out T>, Any>()

    fun <U : T> get(klass: KClass<out T>): U {
        if (!instances.containsKey(klass)) {
            val maybeInstance = createInstanceOf(klass) ?: throw IllegalArgumentException(
                "Unable to access instance of $klass. Its creation rule is undefined."
            )

            if (!klass.isInstance(maybeInstance)) {
                throw TypeCastException("Created instance $maybeInstance is not a type of $klass")
            }

            instances[klass] = maybeInstance
        }

        @Suppress("UNCHECKED_CAST")
        return instances[klass] as U
    }

    protected fun init() {
        instances.clear()
    }

    protected abstract fun createInstanceOf(klass: KClass<out T>): T?
}
