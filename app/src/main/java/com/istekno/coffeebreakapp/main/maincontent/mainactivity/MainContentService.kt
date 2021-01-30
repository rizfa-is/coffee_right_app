package com.istekno.coffeebreakapp.main.maincontent.mainactivity

import retrofit2.http.GET
import retrofit2.http.Path

interface MainContentService {

    @GET("customer/detail/{id}")
    suspend fun getCustomerByID(@Path("id") id: Int): MainContentResponse
}