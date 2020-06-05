package testcase.content

import android.content.SharedPreferences
import com.github.fj.android.content.SharedPreferencesWrapper
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import test.android.content.MockSharedPreferences
import test.com.github.fj.android.util.getRandomAlphaNumericString
import test.com.github.fj.android.util.randomUInt
import testcase.UnitTestBase

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 28 - May - 2020
 */
class SharedPreferencesWrapperTest : UnitTestBase() {
    private val mockSPref = MockSharedPreferences()
    private lateinit var sut: SharedPreferencesWrapper

    @Before
    override fun setup() {
        super.setup()
        this.sut = SharedPreferencesWrapper(mockSPref)
    }

    @Test
    fun `size query returns exact number of items`() {
        // given:
        sut.apply {
            put("k1", 1)
            put("k2", 2L)
            put("k3", 3.0f)
            put("k4", 4.0)
            put("k5", "5")
            put("k6", false)
        }

        // expect:
        assertThat(sut.size, `is`(6))
    }

    @Test
    fun `isEmpty returns exact result`() {
        // given:
        sut.apply {
            put(getRandomAlphaNumericString(4), randomUInt())
        }

        // expect:
        assertThat(sut.size, `is`(1))
        assertThat(sut.isEmpty, `is`(false))

        // given:
        sut.clear()

        // expect:
        assertThat(sut.size, `is`(0))
        assertThat(sut.isEmpty, `is`(true))
    }

    @Test
    fun `get, contains function works perfectly`() {
        // given:
        sut.apply {
            put("k1", 1)
            put("k2", 2L)
            put("k3", 3.0f)
            put("k4", 4.0)
            put("k5", "5")
            put("k6", false)
        }

        // expect:
        listOf("k1", "k2", "k3", "k4", "k5", "k6").forEach {
            assertTrue(sut.contains(it))
        }

        // and:
        assertThat(sut.get("k1", Int.MIN_VALUE), `is`(1))
        assertThat(sut.get("k2", Long.MIN_VALUE), `is`(2L))
        assertThat(sut.get("k3", Float.MIN_VALUE), `is`(3.0f))
        assertThat(sut.get("k4", Double.MIN_VALUE), `is`(4.0))
        assertThat(sut.get("k5", ""), `is`("5"))
        assertThat(sut.get("k6", true), `is`(false))
    }

    @Test
    fun `bulky contains query works properly`() {
        // given:
        sut.apply {
            put("k1", 1)
            put("k2", 2L)
            put("k3", 3.0f)
            put("k4", 4.0)
            put("k5", "5")
            put("k6", false)
        }

        // expect:
        assertTrue(sut.contains("k1", "k5"))
        assertTrue(sut.contains("k1", "k2", "k3", "k4", "k5", "k6"))
    }

    @Test
    fun `remove works properly`() {
        // given:
        sut.apply {
            put("k1", 1)
            put("k2", 2L)
            put("k3", 3.0f)
            put("k4", 4.0)
            put("k5", "5")
            put("k6", false)
        }

        // expect:
        assertThat(sut.size, `is`(6))

        // when:
        sut.remove("k2")

        // then:
        assertFalse(sut.contains("k2"))
        assertThat(sut.size, `is`(5))
    }

    @Test
    fun `bulky remove works properly`() {
        // given:
        sut.apply {
            put("k1", 1)
            put("k2", 2L)
            put("k3", 3.0f)
            put("k4", 4.0)
            put("k5", "5")
            put("k6", false)
        }

        // expect:
        assertThat(sut.size, `is`(6))

        // when:
        sut.remove("k2", "k5")

        // then:
        assertFalse(sut.contains("k2"))
        assertFalse(sut.contains("k5"))
        assertThat(sut.size, `is`(4))
    }

    @Test
    fun `value change event is propagated properly`() {
        // given:
        val notifiedKeys = ArrayList<String>()
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            notifiedKeys.add(key)
        }
        sut.addChangeListener(listener)

        // when:
        sut.apply {
            put("k1", 1)
            put("k2", 2L)
            put("k3", 3.0f)
            put("k4", 4.0)
            put("k5", "5")
            put("k6", false)
        }

        // then:
        assertTrue(notifiedKeys.containsAll(listOf("k1", "k2", "k3", "k4", "k5", "k6")))

        // when:
        sut.removeChangeListener(listener)

        // and:
        sut.apply {
            put("k9999", 9999)
            put("k9998", 9998)
        }

        // then:
        assertThat(notifiedKeys.size, `is`(6))
    }
}
