package com.github.fj.android.io

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 22 - Aug - 2018
 */
import java.io.InputStream
import java.nio.charset.Charset

fun InputStream.dumpAsString(charset: Charset = Charsets.UTF_8): String {
    return this.bufferedReader(charset).use { it.readText() }
}
