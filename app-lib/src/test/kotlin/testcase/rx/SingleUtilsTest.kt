package testcase.rx

import com.github.fj.android.rx.flatMapInner
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test
import testcase.UnitTestBase

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
class SingleUtilsTest : UnitTestBase() {
    @Test
    fun `flatMapInner results a new Single with mapper results`() {
        // given:
        val original = listOf(1, 2, 3, 4, 5)
        val expected = original.map { it.toString() }
        val source = Single.just(original)

        // when:
        val sut = source.flatMapInner {
            it.toString()
        }

        // then:
        val actual = sut.test().singleValue()

        // expect:
        assertNotEquals(source, sut)

        // and:
        assertEquals(expected, actual)
    }

    @Test
    fun `flatMapInner with erroneous mapper results Single#error`() {
        // given:
        val source = listOf(1, 2, 3, 4, 5)

        // when:
        val sut = Single.just(source).flatMapInner {
            throw IllegalArgumentException("Unable to convert")
        }

        // then:
        val actual = sut.test()

        // expect:
        actual.assertError(IllegalArgumentException::class.java)
    }
}
