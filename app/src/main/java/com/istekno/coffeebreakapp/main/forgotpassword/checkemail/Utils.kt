package com.istekno.coffeebreakapp.main.forgotpassword.checkemail

import android.text.TextUtils

class Utils {
    companion object {
        fun isValidEmail(email: String): Boolean {
            return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }
}