package com.istekno.coffeebreakapp.main.forgotpassword.checkemail

import com.google.gson.annotations.SerializedName

data class VerifyEmailResponse(val success: Boolean, val message: String, val data: List<AccountItem>) {
    data class AccountItem(
        @SerializedName("ac_id")
        val acId: Int
    )
}