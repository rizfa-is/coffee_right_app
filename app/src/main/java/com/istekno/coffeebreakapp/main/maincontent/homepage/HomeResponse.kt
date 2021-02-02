package com.istekno.coffeebreakapp.main.maincontent.homepage

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.istekno.coffeebreakapp.main.detailproduct.ListOrderByCsIdResponse
import kotlinx.android.parcel.Parcelize

data class GetProductResponse(
    val success: Boolean,
    val message: String,
    val data: List<DataProduct>
) {
    @Parcelize
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
    ) : Parcelable
}

data class GetOrderResponse(val success: Boolean, val message: String, val data: List<DataOrder>) {
    data class DataOrder(
        @SerializedName("or_id") val orderId: Int,
        @SerializedName("pr_id") val productId: Int,
        @SerializedName("cs_id") val customerId: String,
        @SerializedName("or_status") val orderStatus: String,
        @SerializedName("or_amount") val orderAmount: String,
        @SerializedName("or_price") val orderPrice: String,
        @SerializedName("od_id") val orderDetailId: String,
        @SerializedName("or_created_at") val orderCreated: String,
        @SerializedName("or_updated_at") val orderUpdated: String
    )
}