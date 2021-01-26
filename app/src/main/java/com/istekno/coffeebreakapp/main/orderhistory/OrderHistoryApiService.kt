package com.istekno.coffeebreakapp.main.orderhistory

import retrofit2.http.GET
import retrofit2.http.Path

interface OrderHistoryApiService {

    @GET("orderDetail/getAllByCsId/{id}")
    suspend fun getOrderHistoryByCsId(@Path("id") customerId: Int) : OrderHistoryResponse

}