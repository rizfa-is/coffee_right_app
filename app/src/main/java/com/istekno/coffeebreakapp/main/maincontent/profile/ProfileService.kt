package com.istekno.coffeebreakapp.main.maincontent.profile

import retrofit2.http.GET
import retrofit2.http.Path

interface ProfileService {

    @GET("customer/detail/{id}")
    suspend fun getCustomerByID(@Path("id") id: Int): ProfileResponse
}