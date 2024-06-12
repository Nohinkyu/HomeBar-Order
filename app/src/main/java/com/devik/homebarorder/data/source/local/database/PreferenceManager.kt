package com.devik.homebarorder.data.source.local.database

import android.content.Context
import com.devik.homebarorder.util.Constants.PREFERENCE_NAME
import dagger.hilt.android.qualifiers.ApplicationContext

class PreferenceManager constructor(@ApplicationContext context: Context) {

    private val sharedPreference =
        context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun putString(key: String, value: String) {
        with(sharedPreference.edit()) {
            putString(key, value)
            apply()
        }
    }

    fun putBoolean(key: String, value: Boolean) {
        with(sharedPreference.edit()) {
            putBoolean(key, value)
            apply()
        }
    }

    fun getString(key: String, defValue: String): String {
        return sharedPreference.getString(key, defValue) ?: defValue
    }

    fun getBoolean(key: String, defValue: Boolean): Boolean {
        return sharedPreference.getBoolean(key, defValue)
    }

    fun removeString(key: String) {
        with(sharedPreference.edit()) {
            remove(key)
            apply()
        }
    }
}