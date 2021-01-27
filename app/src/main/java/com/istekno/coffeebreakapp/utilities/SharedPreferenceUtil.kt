package com.istekno.coffeebreakapp.utilities

import android.content.Context
import com.istekno.coffeebreakapp.model.SharedPrefModel

class SharedPreferenceUtil(context: Context) {

    companion object {
        private const val PREF_NAME = "coffee_right_pref"

        private const val ACID = "acID"
        private const val ROLEID = "roleID"
        private const val LEVEL = "level"
        private const val TOKEN = "token"
        private const val LOGIN = "isLogin"

        private const val AC_EMAIL = "AC_EMAIL"
        private const val AC_NAME = "AC_NAME"
        private const val AC_PHONE = "AC_PHONE"
    }

    private val myPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun setPreference(value: SharedPrefModel) {
        val editor = myPreferences.edit()
        value.acID?.let { editor.putInt(ACID, it) }
        value.roleID?.let { editor.putInt(ROLEID, it) }
        value.level?.let { editor.putInt(LEVEL, it) }
        editor.putString(TOKEN, value.token)
        value.isLogin?.let { editor.putBoolean(LOGIN, it) }
        editor.apply()
    }

    fun getPreference(): SharedPrefModel {
        val model = SharedPrefModel()
        model.acID = myPreferences.getInt(ACID, -1)
        model.roleID = myPreferences.getInt(ROLEID, -1)
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