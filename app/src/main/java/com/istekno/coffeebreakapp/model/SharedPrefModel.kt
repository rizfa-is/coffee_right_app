package com.istekno.coffeebreakapp.model

data class SharedPrefModel(
    var acID: Int? = null,
    var acEmail: String? = null,
    var acName: String? = null,
    var acImage: String? = null,
    var acAddress: String? = null,
    var roleID: Int? = null,
    var level: Int? = null,
    var token: String? = null,
    var isLogin: Boolean? = false
)
