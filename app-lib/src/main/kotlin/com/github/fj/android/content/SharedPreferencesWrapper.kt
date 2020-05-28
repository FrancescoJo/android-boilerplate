package com.github.fj.android.content

import android.content.SharedPreferences
import timber.log.Timber

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 28 - May - 2020
 */
class SharedPreferencesWrapper(private val sPref: SharedPreferences) {
    private val listeners = LinkedHashSet<SharedPreferences.OnSharedPreferenceChangeListener>()

    val size: Int
        get() = sPref.all.size

    val isEmpty: Boolean
        get() = sPref.all.isEmpty()

    fun contains(key: String): Boolean = sPref.contains(key)

    fun contains(vararg keys: String): Boolean =
        (keys.filter { contains(it) }).size == keys.size

    fun get(key: String, fallbackValue: Int): Int = getInternal(key) ?: fallbackValue

    fun get(key: String, fallbackValue: Long): Long = getInternal(key) ?: fallbackValue

    fun get(key: String, fallbackValue: Float): Float = getInternal(key) ?: fallbackValue

    fun get(key: String, fallbackValue: Double): Double =
        getInternal<String>(key)?.toDoubleOrNull() ?: fallbackValue

    fun get(key: String, fallbackValue: String): String = getInternal(key) ?: fallbackValue

    fun get(key: String, fallbackValue: Boolean): Boolean = getInternal(key) ?: fallbackValue

    fun put(key: String, data: Int): Int? = putInternal(sPref.edit(), key, data)

    fun put(key: String, data: Long): Long? = putInternal(sPref.edit(), key, data)

    fun put(key: String, data: Float): Float? = putInternal(sPref.edit(), key, data)

    fun put(key: String, data: Double): Double? = putInternal(sPref.edit(), key, data)

    fun put(key: String, data: String?): String? = putInternal(sPref.edit(), key, data)

    fun put(key: String, data: Boolean): Boolean? = putInternal(sPref.edit(), key, data)

    fun remove(key: String): Any? = removeInternal(sPref.edit(), key)

    fun remove(vararg keys: String): Int {
        val edit = sPref.edit()

        return keys.count {
            removeInternal(edit, it) != null
        }
    }

    fun clear(): Int {
        val oldSize = size
        val edit = sPref.edit()
        sPref.all.forEach { (k, _) ->
            removeInternal(edit, k)
        }
        return oldSize
    }

    fun addChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        listeners.add(listener)
    }

    fun removeChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        listeners.remove(listener)
    }

    @Suppress("SameParameterValue", "UNCHECKED_CAST")
    private fun <T> getInternal(key: String): T? = if (contains(key)) {
        try {
            sPref.all[key] as? T
        } catch (e: ClassCastException) {
            Timber.w(e, "Data integrity has been broken!!")
            null
        }
    } else {
        null
    }

    private fun <T> putInternal(
        editor: SharedPreferences.Editor,
        key: String,
        value: T
    ): T? {
        val oldValue = getInternal<T>(key)
        val appliedEditor = when (value) {
            is Int -> editor.putInt(key, value)
            is Long -> editor.putLong(key, value)
            is Float -> editor.putFloat(key, value)
            is Double -> editor.putString(key, value.toString())
            is String -> editor.putString(key, value)
            is Boolean -> editor.putBoolean(key, value)
            else -> {
                throw IllegalArgumentException("Storing $value is unsupported")
            }
        }

        appliedEditor.apply()

        if (oldValue != value) {
            notifyEntryChanged(key)
        }

        return oldValue
    }

    private fun removeInternal(editor: SharedPreferences.Editor, key: String): Any? {
        @Suppress("UNCHECKED_CAST")
        val oldValue = getInternal<Any>(key)
        if (oldValue != null) {
            editor.remove(key).apply()
            notifyEntryChanged(key)
        }
        return oldValue
    }

    private fun notifyEntryChanged(key: String) {
        listeners.forEach {
            it.onSharedPreferenceChanged(sPref, key)
        }
    }
}
