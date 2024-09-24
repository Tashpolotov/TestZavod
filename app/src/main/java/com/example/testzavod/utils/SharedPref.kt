package com.example.testzavod.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.testzavod.domain.model.profile.ProfileModel
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPref @Inject constructor(@ApplicationContext context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val gson = Gson()
    companion object {
        private const val PREF_NAME = "MyPrefs"

    }



    var accessToken: String?
        get() = sharedPreferences.getString(Constants.ACCESS_TOKEN_KEY, null)
        set(value) = sharedPreferences.edit().putString(Constants.ACCESS_TOKEN_KEY, value).apply()

    var refreshToken: String?
        get() = sharedPreferences.getString(Constants.REFRESH_TOKEN_KEY, null)
        set(value) = sharedPreferences.edit().putString(Constants.REFRESH_TOKEN_KEY, value).apply()

    var isFirstRun: Boolean
        get() = sharedPreferences.getBoolean("is_first_run", true)
        set(value) {
            sharedPreferences.edit().putBoolean("is_first_run", value).apply()
        }


    var name: String?
        get() {
            val value = sharedPreferences.getString(Constants.NAME, null)
            return value
        }
        set(value) {
            sharedPreferences.edit().putString(Constants.NAME, value).apply()
        }

    var sureName: String?
        get() {
            val value = sharedPreferences.getString(Constants.SURNAME, null)
            return value
        }
        set(value) {
            sharedPreferences.edit().putString(Constants.SURNAME, value).apply()
        }
    var about: String?
        get() = sharedPreferences.getString(Constants.ABOUT_KEY, null)
        set(value) = sharedPreferences.edit().putString(Constants.ABOUT_KEY, value).apply()

    var birthday: String?
        get() {
            val value = sharedPreferences.getString(Constants.BIRTHDAY, null)
            return value
        }
        set(value) {
            sharedPreferences.edit().putString(Constants.BIRTHDAY, value).apply()
        }

    var city: String?
        get() {
            val value = sharedPreferences.getString(Constants.CITY, null)
            return value
        }
        set(value) {
            sharedPreferences.edit().putString(Constants.CITY, value).apply()
        }

    var cameraPhotoPath: String?
        get() = sharedPreferences.getString("camera_photo_path", null)
        set(value) = sharedPreferences.edit().putString("camera_photo_path", value).apply()

    var galleryPhotoPath: String?
        get() = sharedPreferences.getString("gallery_photo_path", null)
        set(value) = sharedPreferences.edit().putString("gallery_photo_path", value).apply()

}


