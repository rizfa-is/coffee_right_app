package com.istekno.coffeebreakapp.main.maincontent.order

import retrofit2.http.GET

interface OrderService {

    @GET("order/{id}")
    suspend fun getAllProduct(): OrderResponse
}