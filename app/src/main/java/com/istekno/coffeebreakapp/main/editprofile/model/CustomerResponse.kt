package com.istekno.coffeebreakapp.main.editprofile.model

import com.google.gson.annotations.SerializedName

class CustomerResponse(
    val success: Boolean,
    val message: String,
    val data: List<EngineerItem>
) {
    data class EngineerItem(
        @SerializedName("cs_id")
        val csId: Int,

        @SerializedName("ac_id")
        val acId: Int,

        @SerializedName("ac_name")
        val acName: String,

        @SerializedName("ac_email")
        val acEmail: String,

        @SerializedName("ac_phone")
        val acPhone: String,

        @SerializedName("cs_gender")
        val csGender: String,

        @SerializedName("cs_birthday")
        val csBirthday: String,

        @SerializedName("cs_address")
        val csAddress: String
    )
}