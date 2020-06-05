package com.github.fj.android.awesomeapp.core.photo.datasource

import com.github.fj.android.annotation.AllOpen
import com.github.fj.android.awesomeapp.core.MemoryDataSource
import com.github.fj.android.awesomeapp.core.photo.dto.ImageDetailDto
import java.util.concurrent.TimeUnit

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
@AllOpen
internal class PhotoApiMemDataSource : MemoryDataSource() {
    fun putAll(data: List<ImageDetailDto>) {
        super.put(TAG_ALL, data)
    }

    fun getAll(): List<ImageDetailDto> {
        return super.get(TAG_ALL)
    }

    override fun expiresIn() = DEFAULT_EXPIRATION

    companion object {
        private const val TAG_ALL = "kTagApiAll"

        private val DEFAULT_EXPIRATION = Pair(1L, TimeUnit.MINUTES)
    }
}
