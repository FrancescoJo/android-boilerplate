package com.github.fj.android.awesomeapp.datasource

import com.github.fj.android.awesomeapp.exception.CachedDataException
import com.github.fj.android.awesomeapp.exception.DataExpiredException
import com.github.fj.android.awesomeapp.exception.DataNotCachedException
import java.util.concurrent.TimeUnit

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
internal abstract class MemoryDataSource : DataSource {
    private val cache = HashMap<String, Pair<Long, *>>()

    /**
     * @return previous data tagged with `tag`.
     */
    protected inline fun <reified T> put(tag: String, data: T): T? {
        val oldEntry = cache.put(tag, createEntry(data))
        val oldData = oldEntry?.second ?: return null

        return oldData as? T
    }

    @Throws(CachedDataException::class)
    protected fun <T> get(tag: String): T {
        val cachedEntry = cache[tag] ?: throw DataNotCachedException("This data was never cached")
        val data = cachedEntry.second

        expiresIn()?.let {
            val delta = System.nanoTime() - cachedEntry.first
            val expiration = it.second.toNanos(it.first)
            if (delta > expiration) {
                throw DataExpiredException()
            }
        }

        // This conversion is intentional - it will cause ClassCastException if there is/are error.
        @Suppress("UNCHECKED_CAST")
        return data as T
    }

    private fun <T> createEntry(data: T): Pair<Long, T> = Pair(System.nanoTime(), data)

    /**
     * @return `null` or 0 of time pair, if data never expires.
     */
    protected abstract fun expiresIn(): Pair<Long, TimeUnit>?
}
