package com.github.fj.android.awesomeapp

import android.content.Context
import androidx.lifecycle.Lifecycle

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
interface CoreApplication {
    val isUnderInstrumentation: Boolean

    val coreLoggingLevel: Int

    val currentLifecycle: Lifecycle.Event

    val appContext: Context

    val baseApiUrl: String
}
