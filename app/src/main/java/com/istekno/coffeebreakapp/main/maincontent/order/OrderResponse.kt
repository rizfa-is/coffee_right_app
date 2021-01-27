package com.istekno.coffeebreakapp.main.maincontent.order

import com.google.gson.annotations.SerializedName

data class OrderResponse(val success: Boolean, val message: String, val data: List<DataProduct>) {
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