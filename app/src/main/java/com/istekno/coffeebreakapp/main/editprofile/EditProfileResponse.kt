package com.istekno.coffeebreakapp.main.editprofile

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import okhttp3.RequestBody

class EditProfileResponse(
    val success: Boolean,
    val message: String,
    val data: List<ProfileItem>
) {
    data class ProfileItem(
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
        val csGender: RequestBody,

        @SerializedName("cs_birthday")
        val csBirthday: RequestBody,

        @SerializedName("cs_address")
        val csAddress: RequestBody?,

        @SerializedName("cs_image")
        val image: MultipartBody.Part?

    )
}