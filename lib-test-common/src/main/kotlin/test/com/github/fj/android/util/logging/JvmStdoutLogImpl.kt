package test.com.github.fj.android.util.logging

import android.annotation.SuppressLint
import android.util.Log
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
class JvmStdoutLogImpl : Timber.Tree() {
    // Code is for internal use only
    @SuppressLint("SimpleDateFormat")
    private val dateFormat = object : ThreadLocal<SimpleDateFormat>() {
        override fun initialValue() = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
    }

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        val timestamp = requireNotNull(dateFormat.get()).format(Date())
        val (level, stream) = when (priority) {
            Log.VERBOSE -> "VERBOSE" to System.out
            Log.DEBUG -> "DEBUG  " to System.out
            Log.INFO -> "INFO   " to System.out
            Log.WARN -> "WARN   " to System.err
            Log.ERROR -> "ERROR  " to System.err
            Log.ASSERT -> "ASSERT " to System.err
            else -> "DEBUG  " to System.out
        }
        val goodTag = tag?.substring(0, tag.length.coerceAtMost(23)) ?: "STDOUT"

        val msg = String.format("%s [%-23s] %s: %s", timestamp, goodTag, level, message)

        stream.println(msg)
    }
}
