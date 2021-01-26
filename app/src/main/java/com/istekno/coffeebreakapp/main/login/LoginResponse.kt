package com.istekno.coffeebreakapp.main.login

import com.google.gson.annotations.SerializedName

data class LoginResponse(val success: Boolean, val message: String, val data: Data?) {
    data class Data(
        @SerializedName("cs_id") val customerId: Int,
        @SerializedName("ac_id") val accountId: Int,
        @SerializedName("ac_name") val accountName: String,
        @SerializedName("ac_level") val accountLevel: Int,
        @SerializedName("ac_email") val accountEmail: String,
        @SerializedName("ac_phone") val AccountPhone: String,
        val token: String
    )
}