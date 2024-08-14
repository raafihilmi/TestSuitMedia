package com.bumantra.testsuitmedia.data.local

import android.content.Context

internal class UserPreference(context: Context) {
    companion object {
        private const val PREFS_NAME = "user_pref"
        private const val NAME = "name"
    }
    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    fun setUser(value: String) {
        val editor = preferences.edit()
        editor.putString(NAME, value)
        editor.apply()
    }

    fun getUser(): String {
        return preferences.getString(NAME, "") ?: ""
    }
}