package com.istekno.coffeebreakapp.main.maincontent.orderhistory

import com.istekno.coffeebreakapp.main.maincontent.order.OrderResponse
import com.istekno.coffeebreakapp.main.maincontent.orderhistory.detail.DetailOrderHistoryResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface OrderHistoryApiService {

    @GET("orderDetail/getAllByCsId/{id}")
    suspend fun getOrderHistoryByCsId(@Path("id") customerId: Int) : OrderHistoryResponse

    @GET("order/historyOrderByOdId/{id}")
    suspend fun getAllHistoryOrderByOdId(@Path("id") orderDetailId: Int) : DetailOrderHistoryResponse

    @GET("orderDetail/getAllOD")
    suspend fun getAllOrderByAdmin() : OrderHistoryResponse

}