package com.istekno.coffeebreakapp.main.orderhistory.detail

import com.google.gson.annotations.SerializedName
import com.istekno.coffeebreakapp.main.orderhistory.OrderHistoryResponse

data class DetailOrderHistoryResponse(val success: Boolean, val message: String, val data: List<Data>) {
    data class Data(
        @SerializedName("pr_name") val productName: String,
        @SerializedName("or_price") val orderPrice: Int,
        @SerializedName("od_status") val orderDetailStatus: String,
        @SerializedName("or_amount") val orderAmount: Int
    )
}