package com.github.fj.android.widget

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.URLSpan
import android.view.View
import android.widget.TextView
import com.github.fj.android.text.toHtml

fun TextView.setHtmlText(html: String, onClick: () -> Unit) {
    val sequence = html.toHtml()
    val strBuilder = SpannableStringBuilder(sequence)
    val urls = strBuilder.getSpans(0, sequence.length, URLSpan::class.java)
    for (span in urls) {
        makeLinkClickable(strBuilder, span, onClick)
    }

    this.apply {
        text = strBuilder
        movementMethod = LinkMovementMethod.getInstance()
    }
}

private fun makeLinkClickable(ssb: SpannableStringBuilder, span: URLSpan, clicked: () -> Unit) =
    with(ssb) {
        val start = getSpanStart(span)
        val end = getSpanEnd(span)
        val flags = getSpanFlags(span)
        val clickable = object : ClickableSpan() {
            override fun onClick(view: View) {
                clicked()
            }
        }
        setSpan(clickable, start, end, flags)
        removeSpan(span)
    }
