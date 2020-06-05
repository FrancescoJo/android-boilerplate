package testcase.core

import com.github.fj.android.awesomeapp.core.MemoryDataSource
import com.github.fj.android.awesomeapp.exception.DataExpiredException
import com.github.fj.android.awesomeapp.exception.DataNotCachedException
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import test.com.github.fj.android.util.getRandomAlphaNumericString
import testcase.UnitTestBase
import java.util.concurrent.TimeUnit

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
class MemoryDataSourceTest : UnitTestBase() {
    @Test
    fun `get with same tag returns cached value`() {
        // given:
        val sut = SutNeverExpires()
        val tag = "tag"

        // and:
        val expected = "CACHE_TARGET"

        // when:
        sut.putAny(tag, expected)

        // then:
        val actual = sut.getAny(tag)

        // expect:
        assertEquals(expected, actual)
    }

    @Test
    fun `consecutive put returns previous value`() {
        // given:
        val sut = SutNeverExpires()
        val tag = "tag"

        // and:
        val expected = "OLD_VALUE"

        // when:
        val maybeOld1 = sut.putAny(tag, expected)

        // then:
        assertNull(maybeOld1)

        // when:
        val actual = sut.putAny(tag, getRandomAlphaNumericString(8))

        // then:
        assertEquals(expected, actual)
    }

    @Test(expected = DataNotCachedException::class)
    fun `DataNotCachedException occurs when accessing never cached data`() {
        // given:
        val sut = SutNeverExpires()

        // when:
        sut.getAny("tag")
    }

    @Test(expected = DataExpiredException::class)
    fun `DataExpiredException occurs when accessing to expired data`() {
        // given:
        val sut = SutImmediatelyExpires()
        val tag = "tag"

        // and:
        sut.putAny(tag, getRandomAlphaNumericString(8))

        // when:
        sut.getAny(tag)
    }

    private abstract class Sut : MemoryDataSource() {
        fun putAny(tag: String, data: Any): Any? {
            return super.put(tag, data)
        }

        fun getAny(tag: String): Any {
            return super.get(tag)
        }
    }

    private class SutNeverExpires : Sut() {
        override fun expiresIn(): Pair<Long, TimeUnit>? = null
    }

    private class SutImmediatelyExpires : Sut() {
        override fun expiresIn(): Pair<Long, TimeUnit>? = Pair(0, TimeUnit.NANOSECONDS)
    }
}
 