package com.istekno.coffeebreakapp.model

data class SharedPrefModel(
    var acID: Int? = null,
    var acEmail: String? = "Data not set",
    var acName: String? = "Data not set",
    var acImage: String? = null,
    var acAddress: String? = "Data not set",
    var roleID: Int? = null,
    var level: Int? = null,
    var token: String? = null,
    var isLogin: Boolean? = false
)
