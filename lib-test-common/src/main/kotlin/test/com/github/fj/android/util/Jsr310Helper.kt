package test.com.github.fj.android.util

/**
 * All logic depends on JSR-310 Android backport library is not executable until `AndroidThreeTen.init()`
 * is called; however the invocation requires Android context, thus introducing the library makes
 * every local unit tests requires Robolectric. And this little utility solves the problem.
 *
 * This utility is not required anymore if minSDK level of dependant module is 26 or higher.
 *
 * [java.time package API Level](https://developer.android.com/reference/java/time/package-summary)
 *
 * [TZDB File location](https://github.com/ThreeTen/threetenbp)
 *
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
import org.threeten.bp.zone.TzdbZoneRulesProvider
import org.threeten.bp.zone.ZoneRulesInitializer
import org.threeten.bp.zone.ZoneRulesProvider
import java.io.FileInputStream
import java.util.concurrent.atomic.AtomicBoolean

private val initialized = AtomicBoolean()

fun initJsr310Backport() {
    if (!initialized.getAndSet(true)) {
        ZoneRulesInitializer.setInitializer(TzdbInitializer())
    }
}

private class TzdbInitializer : ZoneRulesInitializer() {
    override fun initializeProviders() {
        val tzDbFile = getClasspathResource("TZDB.dat")
        FileInputStream(tzDbFile).use {
            val provider = TzdbZoneRulesProvider(it)
            ZoneRulesProvider.registerProvider(provider)
        }
    }
}
