package com.istekno.coffeebreakapp.main.orderhistory

import com.google.gson.annotations.SerializedName

data class OrderHistoryModel(
    val orderId: Int?,
    val customerId: Int?,
    val deliveryId: Int?,
    val priceBeforeTax: Int?,
    val couponId: Int?,
    val totalPrice: Int?,
    val orderStatus: String?,
    val orderPayment: String?,
    val orderTax: Int?,
    val orderCreated: String?,
    val orderUpdated: String?
)