package com.istekno.coffeebreakapp.main.maincontent.profile

import com.google.gson.annotations.SerializedName

data class ProfileResponse(val success: Boolean, val message: String, val data: List<Data>?) {
    data class Data(@SerializedName("cs_id") val customerId: Int,
                    @SerializedName("ac_id") val accountId: Int,
                    @SerializedName("ac_name") val accountName: String?,
                    @SerializedName("ac_email") val accountEmail: String?,
                    @SerializedName("ac_phone") val accountPhone: String?,
                    @SerializedName("cs_gender") val accountGender: String?,
                    @SerializedName("cs_birthday") val accountBirthday: String?,
                    @SerializedName("cs_address") val accountAddress: String?,
                    @SerializedName("cs_image") val accountImage: String?)
}