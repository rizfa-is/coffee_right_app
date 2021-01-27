package com.istekno.coffeebreakapp.main.orderhistory

import com.istekno.coffeebreakapp.main.orderhistory.detail.DetailOrderHistoryResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface OrderHistoryApiService {

    @GET("orderDetail/getAllByCsId/{id}")
    suspend fun getOrderHistoryByCsId(@Path("id") customerId: Int) : OrderHistoryResponse

    @GET("order/historyOrderByOdId/{id}")
    suspend fun getAllHistoryOrderByOdId(@Path("id") orderDetailId: Int) : DetailOrderHistoryResponse

}