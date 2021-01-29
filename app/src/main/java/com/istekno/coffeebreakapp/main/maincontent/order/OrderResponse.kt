package com.istekno.coffeebreakapp.main.maincontent.order

import com.google.gson.annotations.SerializedName

data class OrderResponse(val success: Boolean, val message: String, val data: List<Data>) {
        data class Data(
                @SerializedName("od_id") val orderDetailId: Int,
                @SerializedName("cs_id") val customerId: Int,
                @SerializedName("dv_id") val deliveryId: Int,
                @SerializedName("od_total_price_before_tax") val priceBeforeTax: Int,
                @SerializedName("co_id") val couponId: Int,
                @SerializedName("od_totalPrice") val totalPrice: Int,
                @SerializedName("od_status") val orderDetailStatus: String,
                @SerializedName("od_payment_method") val orderPayment: String,
                @SerializedName("od_tax") val orderTax: Int,
                @SerializedName("od_created_at") val orderCreated: String,
                @SerializedName("od_updated_at") val orderUpdated: String
        )
}