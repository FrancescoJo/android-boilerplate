package testcase

import io.reactivex.observers.TestObserver
import org.junit.After
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.mockito.Mockito
import test.com.github.fj.android.util.logging.JvmStdoutLogImpl
import timber.log.Timber

/**
 * Template class for test fixtures those not require Android context.
 *
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
abstract class UnitTestBase {
    inline fun <reified T : Any> mock(): T = Mockito.mock(T::class.java)

    @Before
    open fun setup() {
    }

    @After
    open fun tearDown() {
    }

    protected fun <T> TestObserver<T>.singleValue(): T {
        return this.values().first()
    }

    companion object {
        private val STDOUT_LOGGER = JvmStdoutLogImpl()

        @JvmStatic
        @BeforeClass
        fun beforeAll() {
            Timber.plant(STDOUT_LOGGER)
        }

        @JvmStatic
        @AfterClass
        fun afterAll() {
            Timber.uproot(STDOUT_LOGGER)
        }
    }
}
