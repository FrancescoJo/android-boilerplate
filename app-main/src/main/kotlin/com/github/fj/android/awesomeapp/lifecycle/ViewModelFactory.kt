package com.github.fj.android.awesomeapp.lifecycle

import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
@Singleton
class ViewModelFactory @Inject constructor(
    private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {
    @NonNull
    override fun <T : ViewModel?> create(@NonNull maybeVmType: Class<T>): T {
        var creator = viewModels[maybeVmType]
        // In case of some supertype is given of certain ViewModel class
        if (creator == null) {
            for ((key, value) in viewModels) {
                if (maybeVmType.isAssignableFrom(key)) {
                    creator = value
                    break
                }
            }
        }

        requireNotNull(creator) { "No ViewModel is defined for given type: $maybeVmType" }

        // Is checked by call site (and their responsibilities to handle ClassCastException)
        @Suppress("UNCHECKED_CAST")
        return creator.get() as T
    }
}
