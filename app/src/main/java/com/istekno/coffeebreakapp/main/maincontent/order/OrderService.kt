package com.istekno.coffeebreakapp.main.maincontent.order

import com.istekno.coffeebreakapp.main.maincontent.orderhistory.detail.DetailOrderHistoryResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface OrderService {

    @GET("orderDetail/getAllByCsId/{id}")
    suspend fun getOrderByCsId(@Path("id") customerId: Int) : OrderResponse

    @GET("order/historyOrderByOdId/{id}")
    suspend fun getAllHistoryOrderByOdId(@Path("id") orderDetailId: Int) : DetailOrderHistoryResponse
}