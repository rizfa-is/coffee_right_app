package com.istekno.coffeebreakapp.utilities

import android.content.Context
import com.istekno.coffeebreakapp.model.SharedPrefModel

class SharedPreferenceUtil(context: Context) {

    companion object {
        private const val PREF_NAME = "coffee_right_pref"

        private const val AC_ID = "acID"
        private const val AC_EMAIL = "acEmail"
        private const val AC_NAME = "acName"
        private const val AC_IMAGE = "acImage"
        private const val ROLE_ID = "roleID"
        private const val LEVEL = "level"
        private const val TOKEN = "token"
        private const val LOGIN = "isLogin"
    }

    private val myPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun setPreference(value: SharedPrefModel) {
        val editor = myPreferences.edit()

        value.acID?.let { editor.putInt(AC_ID, it) }
        value.roleID?.let { editor.putInt(ROLE_ID, it) }
        value.level?.let { editor.putInt(LEVEL, it) }
        value.isLogin?.let { editor.putBoolean(LOGIN, it) }
        editor.putString(AC_EMAIL, value.acEmail)
        editor.putString(AC_NAME, value.acName)
        editor.putString(AC_IMAGE, value.acImage)
        editor.putString(TOKEN, value.token)

        editor.apply()
    }

    fun getPreference(): SharedPrefModel {
        val model = SharedPrefModel()
        model.acID = myPreferences.getInt(AC_ID, -1)
        model.acEmail = myPreferences.getString(AC_EMAIL, "Not set")
        model.acName = myPreferences.getString(AC_NAME, "Not set")
        model.acImage = myPreferences.getString(AC_IMAGE, "")
        model.roleID = myPreferences.getInt(ROLE_ID, -1)
        model.level = myPreferences.getInt(LEVEL, -1)
        model.token = myPreferences.getString(TOKEN, "")
        model.isLogin = myPreferences.getBoolean(LOGIN, false)

        return model
    }

    fun clear() {
        myPreferences.edit()
            .clear()
            .apply()
    }
}