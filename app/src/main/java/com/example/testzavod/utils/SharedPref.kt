package com.example.testzavod.utils

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPref @Inject constructor(@ApplicationContext context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREF_NAME = "MyPrefs"
    }



    var accessToken: String?
        get() = sharedPreferences.getString(Constants.ACCESS_TOKEN_KEY, null)
        set(value) = sharedPreferences.edit().putString(Constants.ACCESS_TOKEN_KEY, value).apply()

    var refreshToken: String?
        get() = sharedPreferences.getString(Constants.REFRESH_TOKEN_KEY, null)
        set(value) = sharedPreferences.edit().putString(Constants.REFRESH_TOKEN_KEY, value).apply()



}


