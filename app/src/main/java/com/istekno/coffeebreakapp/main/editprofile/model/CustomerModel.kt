package com.istekno.coffeebreakapp.main.editprofile.model

data class CustomerModel(
    val csId: Int? = 0,
    val acId: Int? = 0,
    val acName: String? = null,
    val acPhone: String? = null,
    val csGender: String? = null,
    val csBirthday: String? = null,
    val csImage: String? = null
)