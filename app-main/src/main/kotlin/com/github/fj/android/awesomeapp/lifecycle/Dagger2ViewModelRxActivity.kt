package com.github.fj.android.awesomeapp.lifecycle

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.*
import javax.inject.Inject
import kotlin.reflect.KClass

/**
 * A template class for Android activity that utilities Dagger2 + AAC ViewModel + RxJava.
 *
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
abstract class Dagger2ViewModelRxActivity<T : ViewModel> : DaggerAppCompatActivity() {
    /**
     * Although this field is exposed as public, don't use it directly. It's because of Dagger's
     * limitation.
     */
    @Inject
    lateinit var vmf: ViewModelFactory

    protected abstract val viewModelType: KClass<T>?

    /**
     * @return An instance of [viewModelType] if it is non-null, or [NullPointerException]
     * will be thrown
     */
    protected val viewModel: T
        get() {
            if (viewModelType == null) {
                throw NullPointerException("No viewModelType is defined for $this")
            }

            // Rare cases, may happen under Robolectric or during configuration changes
            if (_viewModel == null) {
                throw NullPointerException(
                    "Accessing to viewModel for $viewModelType of $this " +
                            "is impossible during configuration changes"
                )
            }

            return requireNotNull(_viewModel) {
                // May happen during configuration changes
                "ViewModel of type '$viewModelType' is destroyed unexpectedly"
            }
        }

    private val disposables = EnumMap<Lifecycle.Event, CompositeDisposable>(
        Lifecycle.Event::class.java
    ).apply {
        Lifecycle.Event.values().forEach {
            put(it, CompositeDisposable())
        }
    }

    private var _viewModel: T? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModelType?.let {
            this._viewModel = ViewModelProvider(this, vmf).get(it.java)
        }

        disposablesOf(Lifecycle.Event.ON_CREATE).clear()
    }

    override fun onStart() {
        super.onStart()
        disposablesOf(Lifecycle.Event.ON_START).clear()
    }

    override fun onResume() {
        super.onResume()
        disposablesOf(Lifecycle.Event.ON_RESUME).clear()
    }

    override fun onPause() {
        disposablesOf(Lifecycle.Event.ON_PAUSE).clear()
        super.onPause()
    }

    override fun onStop() {
        disposablesOf(Lifecycle.Event.ON_STOP).clear()
        super.onStop()
    }

    override fun onDestroy() {
        disposablesOf(Lifecycle.Event.ON_DESTROY).clear()
        disposables.forEach { it.value.dispose() }
        super.onDestroy()
    }

    protected fun Disposable.until(event: Lifecycle.Event) {
        disposablesOf(event).add(this)
    }

    private fun disposablesOf(event: Lifecycle.Event): CompositeDisposable {
        if (disposables[event]?.isDisposed == true) {
            disposables[event] = CompositeDisposable()
        }

        return disposables[event]!!
    }
}
