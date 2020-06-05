package com.github.fj.android

import androidx.annotation.CallSuper
import timber.log.Timber

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
@Suppress("UnnecessaryAbstractClass") // Intentional. Template class.
abstract class SingletonHolderTemplate<T> {
    // This field is backed with property 'application'
    @Suppress("ObjectPropertyName")
    private var _instance: T? = null

    val instance: T
        get() = _instance ?: throw IllegalStateException("Singleton instance is not provided")

    @CallSuper
    open fun setInstance(instance: T?) {
        if (this._instance != null && instance != null) {
            val msg = "'%s' will be overwritten to '%s'. Is this behaviour intended?"
            Timber.w(msg, this._instance?.toString() ?: "null", instance)
        }
        this._instance = instance
    }
}
