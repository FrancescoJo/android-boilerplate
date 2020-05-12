package testcase.text

import com.github.fj.android.text.toDaysSince
import com.github.fj.android.text.toMonthsSince
import com.github.fj.android.text.toWeeksSince
import com.github.fj.android.text.toYearsSince
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.threeten.bp.LocalDateTime
import test.com.github.fj.android.util.initJsr310Backport

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
@Suppress("NonAsciiCharacters")
class ISO8601DurationConverterTest {
    @Before
    fun setup() {
        initJsr310Backport()
    }

    @Test
    fun `subscriptionPeriod conversion fails to 0 if given pattern is strange`() {
        // given:
        val now = LocalDateTime.of(2020, 1, 1, 0, 0, 0)
        val pattern = ""

        // when:
        val func = listOf(
            pattern.toDaysSince(now),
            pattern.toWeeksSince(now),
            pattern.toMonthsSince(now),
            pattern.toYearsSince(now)
        )

        // then:
        val actual = func.random()

        // expect:
        assertThat(actual, `is`(0))
    }

    @Test
    fun `period to days conversion succeeds for random patterns`() {
        // given:
        val now = LocalDateTime.of(2020, 1, 1, 0, 0, 0)

        // when:
        val params = listOf(
            "P1Y" to 366,       // year 2020: leap year
            "P1M" to 31,        // 31 days since Jan - 2020
            "P4W" to 28,        // 4 weeks = 28
            "P5D" to 5,
            "P1M2W" to 45       // 31 days + 2 weeks
        )

        // then:
        val actual = params.map { it.first.toDaysSince(now) }

        // expect:
        assertTrue(actual.conforms("days", now, params))
    }

    @Test
    fun `period to weeks conversion succeeds for random patterns`() {
        // given:
        val now = LocalDateTime.of(2020, 1, 1, 0, 0, 0)

        // when:
        val params = listOf(
            "P1Y" to 52,        // year 2020: 52 weeks
            "P1M" to 4,         // 4 weeks in Jan - 2020
            "P4W" to 4,         // 4 weeks = 4
            "P5D" to 0,
            "P1M2W" to 6        // 45 days = 6.4 weeks
        )

        // then:
        val actual = params.map { it.first.toWeeksSince(now) }

        // expect:
        assertTrue(actual.conforms("weeks", now, params))
    }

    @Test
    fun `period to months conversion succeeds for random patterns`() {
        // given:
        val now = LocalDateTime.of(2020, 1, 1, 0, 0, 0)

        // when:
        val params = listOf(
            "P1Y" to 12,
            "P1M" to 1,
            "P4W" to 0,
            "P5D" to 0,
            "P31D" to 1,
            "P1M2W" to 1
        )

        // then:
        val actual = params.map { it.first.toMonthsSince(now) }

        // expect:
        assertTrue(actual.conforms("months", now, params))
    }

    @Test
    fun `period to years conversion succeeds for random patterns`() {
        // given:
        val now = LocalDateTime.of(2020, 1, 1, 0, 0, 0)

        // when:
        val params = listOf(
            "P1Y" to 1,
            "P1M" to 0,
            "P4W" to 0,
            "P5D" to 0,
            "P31D" to 0,
            "P1M2W" to 0
        )

        // then:
        val actual = params.map { it.first.toYearsSince(now) }

        // expect:
        assertTrue(actual.conforms("years", now, params))
    }

    private fun List<Int>.conforms(
        unit: String,
        now: LocalDateTime,
        expected: List<Pair<String, Int>>
    ): Boolean {
        var result = true

        forEachIndexed { i, actual ->
            val expression = expected[i].first
            val expectedLength = expected[i].second

            result = try {
                assertThat(
                    "$expression = $expectedLength $unit since $now",
                    actual,
                    `is`(expectedLength)
                )
                result and true
            } catch (e: AssertionError) {
                e.printStackTrace()
                false
            }
        }

        return result
    }
}
