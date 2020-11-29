package com.example.thebooklibrary.model.datasources

import android.content.SharedPreferences
import com.example.thebooklibrary.util.EMAIL_PREF
import com.example.thebooklibrary.util.TOKEN_PREF
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPrefSourceImpl @Inject constructor(private val appPref: SharedPreferences) : AppPrefSource {

    override fun saveToken(token: String) {
        appPref.edit().apply {
            putString(TOKEN_PREF, token)
            apply()
        }
    }

    override fun getToken() = appPref.getString(TOKEN_PREF, "")!!

    override fun saveEmail(email: String) {
        appPref.edit().apply {
            putString(EMAIL_PREF, email)
            apply()
        }
    }

    override fun getEmail() = appPref.getString(EMAIL_PREF, "")!!
}