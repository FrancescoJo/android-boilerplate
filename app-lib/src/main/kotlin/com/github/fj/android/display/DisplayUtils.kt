package com.github.fj.android.display

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
import android.content.res.Resources
import android.os.Build
import android.text.TextUtils
import android.view.View
import com.github.fj.android.LibApplicationHolder
import java.util.*

fun Int.pxToDp(): Int = (this * getCurrentDisplayMetrics().density).toInt()

fun Float.dpToPx(): Int = (this * getCurrentDisplayMetrics().density).toInt()

fun getStatusBarHeight(): Int {
    val resourceId = getAndroidDimenResId("status_bar_height")
    return if (resourceId > 0) {
        getSysResources().getDimensionPixelSize(resourceId)
    } else {
        0
    }
}

fun getNavBarHeight(): Int {
    val resourceId = getAndroidDimenResId("navigation_bar_height")
    return if (resourceId > 0) {
        getSysResources().getDimensionPixelSize(resourceId)
    } else {
        0
    }
}

fun isRtlConfiguration(): Boolean =
    TextUtils.getLayoutDirectionFromLocale(getDefaultLocale()) == View.LAYOUT_DIRECTION_RTL

fun getDefaultLocale(): Locale =
    LibApplicationHolder.instance.let {
        return@let if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            it.resources.configuration.locales[0]
        } else {
            @Suppress("DEPRECATION")
            it.resources.configuration.locale
        }
    }

private fun getSysResources() = Resources.getSystem()

private fun getCurrentDisplayMetrics() = getSysResources().displayMetrics

private fun getAndroidDimenResId(name: String) =
    getSysResources().getIdentifier(name, "dimen", "android")
