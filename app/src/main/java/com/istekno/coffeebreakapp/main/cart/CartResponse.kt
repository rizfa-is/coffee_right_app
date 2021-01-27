package com.istekno.coffeebreakapp.main.cart

import com.google.gson.annotations.SerializedName

class CartResponse(val success: Boolean, val message: String, val data: List<DataCart>) {
    data class DataCart(
        @SerializedName("or_id") val orderId: Int,
        @SerializedName("pr_id") val productId: Int,
        @SerializedName("pr_name") val productName: String,
        @SerializedName("pr_image") val productImage: String,
        @SerializedName("cs_id") val customerId: Int,
        @SerializedName("or_status") val orderStatus: String,
        @SerializedName("or_amount") val orderAmount: String,
        @SerializedName("or_price") val orderPrice: String,

        @SerializedName("or_created_at") val orderCreated: String,
        @SerializedName("or_updated_at") val orderUpdated: String
    )

}

class CartPriceResponse(val success: Boolean, val message: String, val data: List<DataPriceCart>) {
    data class DataPriceCart(
        @SerializedName("or_price") val orderPrice: Int,
    )
}

class UpdateOrderResponse(val success: Boolean, val message: String)