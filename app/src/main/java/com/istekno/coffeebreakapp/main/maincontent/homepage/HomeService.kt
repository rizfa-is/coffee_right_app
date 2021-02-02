package com.istekno.coffeebreakapp.main.maincontent.homepage

import retrofit2.http.GET

interface HomeService {

    @GET("product/getAllProduct")
    suspend fun getAllProduct(): GetProductResponse

    @GET("order")
    suspend fun getAllOrder(): GetOrderResponse
}