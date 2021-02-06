package com.istekno.coffeebreakapp.main.maincontent.homepage

import com.istekno.coffeebreakapp.main.cart.CartResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface HomeService {

    @GET("product/getAllProduct")
    suspend fun getAllProduct(): GetProductResponse

    @GET("order")
    suspend fun getAllOrder(): GetOrderResponse

    @GET("order/statusCart/{csId}")
    suspend fun getListCartByCsId(@Path("csId") csId: Int): CartResponse
}