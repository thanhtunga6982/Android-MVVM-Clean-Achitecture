package com.base.android_mvvm_clean_architecture.extension

import android.content.SharedPreferences
import com.google.gson.Gson
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Extensions support SharedPreferences save data using Delegate Property
 */
fun <T> SharedPreferences.objects(
    clazz: Class<T>,
    gson: Gson,
    key: (KProperty<*>) -> String = KProperty<*>::name
): ReadWriteProperty<Any, T?> = object : ReadWriteProperty<Any, T?> {
    override fun getValue(thisRef: Any, property: KProperty<*>): T? {
        val json = getString(key(property), "")
        return if (json.isNullOrEmpty()) null else gson.fromJson(json, clazz)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T?) {
        val json = gson.toJson(value)
        edit().putString(key(property), json).apply()
    }
}

fun <T> SharedPreferences.arrays(
    clazz: Class<Array<T>>,
    gson: Gson,
    key: (KProperty<*>) -> String = KProperty<*>::name
): ReadWriteProperty<Any, List<T>?> = object : ReadWriteProperty<Any, List<T>?> {
    override fun getValue(thisRef: Any, property: KProperty<*>): List<T>? {
        val json = getString(key(property), "")
        return if (json.isNullOrEmpty()) null else {
            val values = gson.fromJson(json, clazz)
            arrayListOf(*values)
        }
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: List<T>?) {
        val json = gson.toJson(value)
        edit().putString(key(property), json).apply()
    }
}

fun SharedPreferences.string(
    defaultValue: String? = null,
    key: (KProperty<*>) -> String = KProperty<*>::name
): ReadWriteProperty<Any, String?> =
    object : ReadWriteProperty<Any, String?> {
        override fun getValue(
            thisRef: Any,
            property: KProperty<*>
        ) = getString(key(property), defaultValue)

        override fun setValue(
            thisRef: Any,
            property: KProperty<*>,
            value: String?
        ) = edit().putString(key(property), value).apply()
    }

fun SharedPreferences.int(
    defaultValue: Int = 0,
    key: (KProperty<*>) -> String = KProperty<*>::name
): ReadWriteProperty<Any, Int> =
    object : ReadWriteProperty<Any, Int> {
        override fun getValue(
            thisRef: Any,
            property: KProperty<*>
        ) = getInt(key(property), defaultValue)

        override fun setValue(
            thisRef: Any,
            property: KProperty<*>,
            value: Int
        ) = edit().putInt(key(property), value).apply()
    }

fun SharedPreferences.boolean(
    defaultValue: Boolean = false,
    key: (KProperty<*>) -> String = KProperty<*>::name
): ReadWriteProperty<Any, Boolean> =
    object : ReadWriteProperty<Any, Boolean> {
        override fun getValue(
            thisRef: Any,
            property: KProperty<*>
        ) = getBoolean(key(property), defaultValue)

        override fun setValue(
            thisRef: Any,
            property: KProperty<*>,
            value: Boolean
        ) = edit().putBoolean(key(property), value).apply()
    }
