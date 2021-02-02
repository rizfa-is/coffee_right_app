package com.istekno.coffeebreakapp.main.cart

import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface CartApiService {

    @GET("order/statusCart/{csId}")
    suspend fun getListCartByCsId(@Path("csId") csId: Int): CartResponse

    @GET("order/statusCart/{csId}")
    suspend fun getCartPriceByCsId(@Path("csId") csId: Int): CartPriceResponse

    @PUT("order/updateOrder/{orId}")
    suspend fun updateOrderPlus(@Path("orId") orId: Int): UpdateOrderResponse

    @PUT("order/updateOrderMin/{orId}")
    suspend fun updateOrderMin(@Path("orId") orId: Int): UpdateOrderResponse

    @DELETE("order/deleteOrder/{orderId}")
    suspend fun deleteOrderById(@Path("orderId") orId: Int): UpdateOrderResponse
}