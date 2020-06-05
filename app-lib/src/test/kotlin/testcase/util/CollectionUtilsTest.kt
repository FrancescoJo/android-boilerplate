/*
 * Copyright 2020 Hyperconnect inc. All rights reserved.
 *
 * Hyperconnect inc. intellectual property.
 * Use of this software is subject to licence terms.
 */
package testcase.util

import com.github.fj.android.util.findInsertPosition
import com.github.fj.android.util.resize
import com.github.fj.android.util.sumLong
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 29 - Jun - 2018
 */
class CollectionUtilsTest {
    @Test
    fun `resize yields correct result upon given data set`() {
        assertEquals(listOf(0)         , listOf(0, 1, 2).resize(1))
        assertEquals(listOf(0, 1)      , listOf(0, 1).resize(2))
        assertEquals(listOf(0, 1, 9, 9), listOf(0, 1).resize(4) { _ -> 9 })
    }

    @Test(expected = IllegalArgumentException::class)
    fun `resize cannot operates with an null filler function`() {
        listOf(0, 1).resize(4, null)
    }

    @Test
    fun `sumLong yields correct result upon given data set`() {
        val values = listOf(
            SumLongTestSubject("", 1L),
            SumLongTestSubject("", 2L),
            SumLongTestSubject("", 3L)
        )
        val expected = 6L

        val actual = values.sumLong { it.points }
        assertEquals(expected, actual)
    }

    @Test
    fun `findInsertPosition yields correct result upon given sorted list`() {
        val values = listOf(1, 3, 4, 5, 6, 8, 9)
        val withPosition: (Int) -> Int = { values.findInsertPosition(it) }

        assertEquals(0, withPosition(0))
        assertEquals(1, withPosition(2))
        assertEquals(4, withPosition(5))
        assertEquals(7, withPosition(10))
    }

    private data class SumLongTestSubject(val name: String, val points: Long)
}
