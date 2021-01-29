package com.istekno.coffeebreakapp.main.editpassword

import com.google.gson.annotations.SerializedName

data class VerifyPasswordResponse(val success: Boolean, val message: String, val data: List<AccountItem>) {
    data class AccountItem(
        @SerializedName("ac_id")
        val acId: Int,

        @SerializedName("ac_name")
        val acName: String,

        @SerializedName("ac_email")
        val acEmail: String,

        @SerializedName("ac_phone")
        val acPhone: String
    )
}