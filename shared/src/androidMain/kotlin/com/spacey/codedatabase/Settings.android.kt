package com.spacey.codedatabase

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

actual class SettingsService(context: Context) {

    private val preference: SharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE)

    actual fun getString(key: String): String? =
        preference.getString(key, null)

    actual fun setString(key: String, value: String) {
        preference.edit {
            putString(key, value)
        }
    }

    actual fun remove(key: String) {
        preference.edit {
            remove(key)
        }
    }

    private fun SharedPreferences.edit(block: SharedPreferences.Editor.() -> SharedPreferences.Editor) {
        this.edit().block().apply()
    }

    companion object {
        const val PREFERENCE_NAME = "code_db"
    }

}