package com.github.fj.android.app

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import timber.log.Timber

/**
 * Moves to Play store app or web page for package name of given [Context].
 *
 * @return `true` if transition was successful, `false` otherwise.
 */
fun Context.moveToPlayStore(): Boolean = moveToPlayStore(packageName)

/**
 * Moves to Play store app or web page for package name of given [Context].
 *
 * @see <a href="https://developer.android.com/distribute/marketing-tools/linking-to-google-play">Linking to Google play</a>
 * @return `true` if transition was successful, `false` otherwise.
 */
fun Context.moveToPlayStore(appPkgName: String): Boolean {
    val playStoreUri = "market://details?id=$appPkgName"
    val playStoreIntent = Intent(Intent.ACTION_VIEW, Uri.parse(playStoreUri))
    val resolveInfo = packageManager.queryIntentActivities(playStoreIntent, 0)
        .firstOrNull { it.activityInfo.applicationInfo.packageName == "com.android.vending" }

    if (resolveInfo == null) {
        Timber.e("Play store is not installed on this device.")
        val url = "http://play.google.com/store/apps/details?id=$appPkgName"
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

        return startActivitySafe(browserIntent)
    }

    playStoreIntent.apply {
        // Play store app must not be included in current app's Task stack
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
        // Immediate page display(speedup) for our app if Play store showed our app lately
        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        // enforces only Play store app('com.android.vending') handles this intent
        component =
            resolveInfo.activityInfo.run { ComponentName(applicationInfo.packageName, name) }
    }

    return startActivitySafe(playStoreIntent)
}

/**
 * A safer version of [Activity.startActivity]. Since the method may fail with
 * various reasons and more worse, it crashes at invocation point. This method prevents such
 * crashes and return results if [Activity.startActivity] actually failed.
 *
 * @return `true` if [Activity.startActivity] succeeded, `false` otherwise.
 */
fun Context.startActivitySafe(intent: Intent): Boolean {
    try {
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        Timber.e("Unable to launch intent: %s", intent)
        return false
    }

    return true
}

fun Activity.startActivitySafe(intent: Intent): Boolean =
    (this as Context).startActivitySafe(intent)

inline fun <reified T : Activity> Context.startActivitySafe(): Boolean =
    startActivitySafe(Intent(this, T::class.java))
