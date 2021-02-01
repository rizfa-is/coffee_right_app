package com.istekno.coffeebreakapp.main.maincontent.orderhistory.detail

data class DetailOrderHistoryModel(
    val productName: String,
    val orderPrice: Int,
    val orderDetailStatus: String,
    val orderAmount: Int
)