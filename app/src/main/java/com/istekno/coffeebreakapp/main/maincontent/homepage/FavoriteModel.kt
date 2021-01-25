package com.istekno.coffeebreakapp.main.maincontent.homepage

data class FavoriteModel(
    val prID: Int,
    val dcID: Int,
    val prName: String? = "Data empty",
    val prDesc: String? = "Data empty",
    val prUnitPrice: Int? = 0,
    val prImage: String? = null,
    val prCategory: String? = "Data empty",
    val prDayStart: String? = "Data empty",
    val prDayEnd: String? = "Data empty",
    val prTimeStart: String? = "Data empty",
    val prTimeEnd: String? = "Data empty"
)
