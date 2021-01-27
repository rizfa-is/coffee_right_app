package com.istekno.coffeebreakapp.main.payment

import com.google.gson.annotations.SerializedName

data class PaymentModel(
    val amount: Int,
    val price: Int,
    val productName: String
)