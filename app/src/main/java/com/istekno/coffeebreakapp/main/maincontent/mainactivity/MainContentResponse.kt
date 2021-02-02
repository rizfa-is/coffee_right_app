package com.istekno.coffeebreakapp.main.maincontent.mainactivity

import com.google.gson.annotations.SerializedName

data class MainContentResponse(val success: Boolean, val message: String, val data: List<Data>?) {
    data class Data(
        @SerializedName("cs_id") val customerId: Int,
        @SerializedName("ac_id") val accountId: Int,
        @SerializedName("ac_name") val accountName: String?,
        @SerializedName("ac_email") val accountEmail: String?,
        @SerializedName("ac_phone") val accountPhone: String?,
        @SerializedName("cs_gender") val accountGender: String?,
        @SerializedName("cs_birthday") val accountBirthday: String?,
        @SerializedName("cs_address") val accountAddress: String?,
        @SerializedName("cs_image") val accountImage: String?
    )
}

data class ProductResponse(val success: Boolean, val message: String, val data: List<DataProduct>) {
    data class DataProduct(
        @SerializedName("pr_id") val productId: Int,
        @SerializedName("dc_id") val discountId: Int,
        @SerializedName("pr_name") val productName: String,
        @SerializedName("pr_desc") val productDesc: String,
        @SerializedName("pr_unit_price") val productPrice: String,
        @SerializedName("pr_image") val productImage: String,
        @SerializedName("pr_favorite") val productFavorite: String,
        @SerializedName("pr_category") val productCategory: String,
        @SerializedName("pr_created_at") val productCreated: String,
        @SerializedName("pr_updated_at") val productUpdated: String
    )
}