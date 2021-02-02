package com.istekno.coffeebreakapp.main.detailproduct

import com.google.gson.annotations.SerializedName

data class DetailProductResponse(
        val success: Boolean,
        val message: String,
        val data: List<DataProduct>
) {
    data class DataProduct(
            @SerializedName("pr_id") val productId: Int,
            @SerializedName("dc_id") val discountId: Int,
            @SerializedName("pr_name") val productName: String,
            @SerializedName("pr_size") val productSize: String,
            @SerializedName("pr_desc") val productDesc: String,
            @SerializedName("pr_unit_price") val productPrice: String,
            @SerializedName("pr_image") val productImage: String,
            @SerializedName("pr_category") val productCategory: String,
            @SerializedName("pr_created_at") val productCreated: String,
            @SerializedName("pr_updated_at") val productUpdated: String
    )
}

data class ListOrderByCsIdResponse(
        val success: Boolean,
        val message: String,
        val data: List<DataOrder>
) {
    data class DataOrder(
            @SerializedName("or_id") val orderId: Int,
            @SerializedName("pr_id") val productId: Int,
            @SerializedName("cs_id") val csId: Int,
            @SerializedName("or_status") val orStatus: String

    )
}