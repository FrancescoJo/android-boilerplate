package com.github.fj.android.awesomeapp

import android.content.Context
import android.os.Build
import android.os.StrictMode
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.facebook.drawee.backends.pipeline.Fresco
import com.github.fj.android.LibApplicationHolder
import com.github.fj.android.annotation.AllOpen
import com.github.fj.android.awesomeapp.inject.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
@AllOpen
class AwesomeAppApplication : DaggerApplication(), LifecycleObserver, CoreApplication {
    override val isUnderInstrumentation = false

    override val coreLoggingLevel = Log.DEBUG

    override var currentLifecycle: Lifecycle.Event = Lifecycle.Event.ON_DESTROY

    override val appContext: Context
        get() = this

    override val baseApiUrl: String
        get() = BuildConfig.BASE_API_URL

    override fun onCreate() {
        LibApplicationHolder.setInstance(this)
        CoreApplicationHolder.setInstance(this)
        super.onCreate()

        initStrictMode()
        installLoggers()
        setupLifeCycle()
        initFresco()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val appComponent = DaggerAppComponent.builder()
            .build()

        appComponent.inject(this)

        return appComponent
    }

    @Suppress("ProtectedMemberInFinalClass") // False positive with @AllOpen
    protected fun initStrictMode() {
        if (BuildConfig.DEBUG) {
            // DetectAll warns everything even by mistakes of 3rd-party libs after Android O.
            val policy = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                StrictMode.VmPolicy.Builder()
                    .penaltyLog()
                    .detectActivityLeaks()
                    .detectCleartextNetwork()
                    .detectContentUriWithoutPermission()
                    .detectFileUriExposure()
                    .detectLeakedClosableObjects()
                    .detectLeakedRegistrationObjects()
                    .detectLeakedSqlLiteObjects()
//                    .detectUntaggedSockets()
//                    .detectNonSdkApiUsage()
            } else {
                StrictMode.VmPolicy.Builder()
                    .penaltyLog()
                    .detectAll()
            }

            StrictMode.setVmPolicy(policy.build())
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .penaltyLog()
                    .detectAll()
                    .build()
            )
        }
    }

    @Suppress("ProtectedMemberInFinalClass") // False positive with @AllOpen
    protected fun installLoggers() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    @Suppress("ProtectedMemberInFinalClass") // False positive with @AllOpen
    protected fun setupLifeCycle() {
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    @Suppress("ProtectedMemberInFinalClass") // False positive with @AllOpen
    protected fun initFresco() {
        Fresco.initialize(this)
    }

    // Called by Android system
    @Suppress("Unused")
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    final fun onMoveToForeground() {
        this.currentLifecycle = Lifecycle.Event.ON_START
    }

    // Called by Android system
    @Suppress("Unused")
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    final fun onMoveToBackground() {
        this.currentLifecycle = Lifecycle.Event.ON_STOP
    }
}
