package com.istekno.coffeebreakapp.main.orderhistory.detail

import com.google.gson.annotations.SerializedName

data class DetailOrderHistoryModel(
    val productName: String,
    val orderPrice: Int,
    val orderDetailStatus: String,
    val orderAmount: Int
)