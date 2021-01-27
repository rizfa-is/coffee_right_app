package com.istekno.coffeebreakapp.main.payment

import com.google.gson.annotations.SerializedName
import com.istekno.coffeebreakapp.main.orderhistory.OrderHistoryResponse

data class PaymentResponse(val success: Boolean, val message: String, val data: List<Data>) {
    data class Data(
        @SerializedName("or_amount") val amount: Int,
        @SerializedName("or_price") val price: Int,
        @SerializedName("pr_name") val productName: String
    )
    data class CreateResponse(val success: Boolean, val message: String)
}

