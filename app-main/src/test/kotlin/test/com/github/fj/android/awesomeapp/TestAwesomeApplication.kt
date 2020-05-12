package test.com.github.fj.android.awesomeapp

import com.github.fj.android.LibApplicationHolder
import com.github.fj.android.awesomeapp.AwesomeAppApplication
import com.github.fj.android.awesomeapp.CoreApplicationHolder

/**
 * Test application used under Robolectric environment.
 *
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
class TestAwesomeApplication : AwesomeAppApplication() {
    override val isUnderInstrumentation = true

    override fun onTerminate() {
        /*
         * Robolectric creates Application instance per each test methods
         * https://github.com/robolectric/robolectric/issues/595
         */
        LibApplicationHolder.setInstance(null)
        CoreApplicationHolder.setInstance(null)
        super.onTerminate()
    }

    override fun initStrictMode() {
        /* no-op */
    }

    override fun installLoggers() {
        /* no-op */
    }

    override fun initFresco() {
        /* no-op */
    }
}
