package com.istekno.coffeebreakapp.model

data class SharedPrefModel(
    var acID: Int? = null,
    var roleID: Int? = null,
    var level: Int? = null,
    var token: String? = null,
    var isLogin: Boolean? = false
)
