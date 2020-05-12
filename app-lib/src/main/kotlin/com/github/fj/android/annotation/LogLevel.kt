package com.github.fj.android.annotation

import android.util.Log
import androidx.annotation.IntDef

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
@IntDef(Log.VERBOSE, Log.DEBUG, Log.INFO, Log.WARN, Log.ERROR, Log.ASSERT)
annotation class LogLevel
