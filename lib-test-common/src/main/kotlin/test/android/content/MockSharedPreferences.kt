package test.android.content

import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import test.android.content.MockSharedPreferences.Operation.PUT
import test.android.content.MockSharedPreferences.Operation.REMOVE

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 28 - May - 2020
 * @see <a href="https://android.googlesource.com/platform/packages/apps/ContactsCommon/+/5ade0bb/TestCommon/src/com/android/contacts/common/test/mocks/MockSharedPreferences.java">AOSP MockSharedPreferences.java</a>
 */
class MockSharedPreferences : SharedPreferences {
    private val sPrefMap = HashMap<String, Any>()
    private val listeners = LinkedHashSet<SharedPreferences.OnSharedPreferenceChangeListener>()

    override fun contains(key: String): Boolean = sPrefMap.containsKey(key)

    override fun getAll(): Map<String, *> = sPrefMap

    override fun getBoolean(key: String, defValue: Boolean): Boolean =
        sPrefMap[key] as? Boolean ?: defValue

    override fun getInt(key: String, defValue: Int): Int =
        sPrefMap[key] as? Int ?: defValue

    override fun getLong(key: String, defValue: Long): Long =
        sPrefMap[key] as? Long ?: defValue

    override fun getFloat(key: String, defValue: Float): Float =
        sPrefMap[key] as? Float ?: defValue

    override fun getString(key: String, defValue: String?): String? =
        sPrefMap[key] as? String ?: defValue

    @Suppress("UNCHECKED_CAST")
    override fun getStringSet(key: String, defValues: Set<String>?): Set<String>? =
        sPrefMap[key] as? Set<String> ?: defValues

    override fun edit(): Editor = MockEditor(this)

    override fun registerOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        listeners.add(listener)
    }

    override fun unregisterOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        listeners.remove(listener)
    }

    private class MockEditor(prefs: MockSharedPreferences) : Editor {
        private val sPref = prefs
        private val originalData = prefs.sPrefMap
        private val pendingData = HashMap<String, DataWrapper>()
        private val listeners = prefs.listeners

        private var mustBeCleared = false

        override fun putBoolean(key: String, value: Boolean): Editor {
            pendingData[key] = DataWrapper(PUT, value)
            return this
        }

        override fun putInt(key: String, value: Int): Editor {
            pendingData[key] = DataWrapper(PUT, value)
            return this
        }

        override fun putLong(key: String, value: Long): Editor {
            pendingData[key] = DataWrapper(PUT, value)
            return this
        }

        override fun putFloat(key: String, value: Float): Editor {
            pendingData[key] = DataWrapper(PUT, value)
            return this
        }

        override fun putString(key: String, value: String?): Editor {
            if (value == null) {
                pendingData[key] = DataWrapper(REMOVE, Unit)
            } else {
                pendingData[key] = DataWrapper(PUT, value)
            }
            return this
        }

        override fun putStringSet(key: String, values: MutableSet<String>?): Editor {
            if (values == null) {
                pendingData[key] = DataWrapper(REMOVE, Unit)
            } else {
                pendingData[key] = DataWrapper(PUT, values)
            }
            return this
        }

        override fun remove(key: String): Editor {
            pendingData[key] = DataWrapper(REMOVE, Unit)
            return this
        }

        override fun clear(): Editor {
            pendingData.clear()
            mustBeCleared = true
            return this
        }

        override fun commit(): Boolean {
            mergeAll()
            return true
        }

        override fun apply() {
            mergeAll()
        }

        private fun mergeAll() {
            if (mustBeCleared) {
                originalData.clear()
            }

            pendingData.entries.forEach {
                val key = it.key
                val wrappedData = it.value
                val oldValue = originalData[key]
                val newValue = wrappedData.data

                when (wrappedData.operation) {
                    PUT -> originalData[key] = newValue
                    REMOVE -> originalData.remove(key)
                }

                if (newValue != oldValue) {
                    listeners.forEach { l -> l.onSharedPreferenceChanged(sPref, key) }
                }
            }
        }
    }

    private data class DataWrapper(
        val operation: Operation,
        val data: Any
    )

    private enum class Operation {
        PUT,
        REMOVE
    }
}
