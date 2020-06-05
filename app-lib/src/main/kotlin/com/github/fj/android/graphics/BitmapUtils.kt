package com.github.fj.android.graphics

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream

val EMPTY_BITMAP: Bitmap = Bitmap.createBitmap(0, 0, Bitmap.Config.ARGB_8888)

fun Bitmap?.recycleSafely() {
    this?.recycle()
}

/**
 * You must perform an [File.exists] check on returned [File] object to determine whether
 * this method has been worked correctly or not.
 */
fun Bitmap.toJpgAs(outputFile: File, compression: Int = 95): File {
    @Suppress("TooGenericExceptionCaught")
    try {
        val out = FileOutputStream(outputFile)
        this.compress(Bitmap.CompressFormat.JPEG, compression, out)
        out.close()
    } catch (e: Throwable) {
        Timber.e(e, "Unexpected error while dumping bitmap as file")
    } finally {
        this.recycle()
    }
    return outputFile
}

fun View.captureToBitmap(): Bitmap {
    if (width > 0 && height > 0) {
        measure(
            View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)
        )
    }
    layout(0, 0, measuredWidth, measuredHeight)

    val bitmap = Bitmap.createBitmap(
        measuredWidth,
        measuredHeight, Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    val background = background

    background?.draw(canvas)
    draw(canvas)

    return bitmap
}
