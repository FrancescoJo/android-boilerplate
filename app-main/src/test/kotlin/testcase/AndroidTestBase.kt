package testcase

import android.app.Activity
import android.os.SystemClock
import androidx.annotation.CallSuper
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.TestScheduler
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import test.com.github.fj.android.awesomeapp.TestAwesomeApplication
import java.util.concurrent.TimeUnit

/**
 * Template class for test fixtures those require Android context.
 *
 * Don't use this for Instrumentation tests.
 *
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
@RunWith(RobolectricTestRunner::class)
@Config(application = TestAwesomeApplication::class)
abstract class AndroidTestBase : UnitTestBase() {
    private val testBackgroundScheduler = TestScheduler()

    protected fun flushBackgroundScheduler() {
        testBackgroundScheduler.triggerActions()
    }

    protected fun flushMainScheduler() {
        Robolectric.flushForegroundThreadScheduler()
    }

    protected fun flushAllSchedulers() {
        flushBackgroundScheduler()
        flushMainScheduler()
    }

    protected fun advanceTimeBy(delayTime: Long, unit: TimeUnit) {
        SystemClock.setCurrentTimeMillis(SystemClock.elapsedRealtime() + unit.toMillis(delayTime))
        testBackgroundScheduler.advanceTimeBy(delayTime, unit)
    }

    protected fun getTestApplication(): TestAwesomeApplication {
        return ApplicationProvider.getApplicationContext() as TestAwesomeApplication
    }

    protected fun <A : Activity> ActivityScenario<A>.getActivity(): A {
        var activityRef: A? = null
        onActivity { activity -> activityRef = activity }

        if (activityRef == null) {
            throw AssertionError("Cannot get Activity reference from Activity scenario instance $this.")
        }

        return activityRef as A
    }

    @Before
    @CallSuper
    override fun setup() {
        super.setup()
        RxJavaPlugins.setIoSchedulerHandler { testBackgroundScheduler }
        RxJavaPlugins.setComputationSchedulerHandler { testBackgroundScheduler }
    }

    @After
    @CallSuper
    override fun tearDown() {
        RxJavaPlugins.setIoSchedulerHandler(null)
        RxJavaPlugins.setComputationSchedulerHandler(null)
        super.tearDown()
    }
}
