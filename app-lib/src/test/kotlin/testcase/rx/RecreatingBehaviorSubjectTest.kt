package testcase.rx

import com.github.fj.android.rx.RecreatingBehaviorSubject
import io.reactivex.subjects.BehaviorSubject
import org.junit.Assert.assertEquals
import org.junit.Test
import testcase.UnitTestBase

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
class RecreatingBehaviorSubjectTest : UnitTestBase() {
    @Test
    fun `PublishSubject by our method is recreated after error`() {
        // given:
        val sut: BehaviorSubject<Int> by RecreatingBehaviorSubject()

        // when:
        val subscriber = sut.test()

        // then:
        sut.onNext(1)
        sut.onNext(2)
        sut.onNext(3)
        sut.onError(IllegalArgumentException(""))

        // expect:
        assertEquals(3, subscriber.valueCount())
        assertEquals(1, subscriber.errorCount())

        // when:
        val subscriber2 = sut.test()

        // expect:
        assertEquals(1, subscriber2.valueCount())

        // then:
        sut.onNext(55555)

        // expect:
        assertEquals(2, subscriber2.valueCount())
        assertEquals(0, subscriber2.errorCount())
    }
}
