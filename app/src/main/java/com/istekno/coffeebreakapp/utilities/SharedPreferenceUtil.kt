package com.istekno.coffeebreakapp.utilities

import android.content.Context
import com.istekno.coffeebreakapp.model.SharedPrefModel

class SharedPreferenceUtil(context: Context) {

    companion object {
        private const val PREF_NAME = "coffee_right_pref"

        private const val ACID = "acID"
        private const val LEVEL = "level"
        private const val TOKEN = "token"
        private const val LOGIN = "isLogin"
    }

    private val myPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun setPreference(value: SharedPrefModel) {
        val editor = myPreferences.edit()
        value.acID?.let { editor.putInt(ACID, it) }
        editor.putString(LEVEL, value.level)
        editor.putString(TOKEN, value.token)
        value.isLogin?.let { editor.putBoolean(LOGIN, it) }
        editor.apply()
    }

    fun getPreference(): SharedPrefModel {
        val model = SharedPrefModel()
        model.acID = myPreferences.getInt(ACID, -1)
        model.level = myPreferences.getString(LEVEL, "")
        model.token = myPreferences.getString(TOKEN, "")
        model.isLogin = myPreferences.getBoolean(LOGIN, false)

        return model
    }
}