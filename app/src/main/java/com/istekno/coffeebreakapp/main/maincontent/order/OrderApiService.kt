package com.istekno.coffeebreakapp.main.maincontent.order

import com.istekno.coffeebreakapp.main.maincontent.orderhistory.detail.DetailOrderHistoryResponse
import com.istekno.coffeebreakapp.main.maincontent.orderhistory.detail.UpdateStatusOrderDetailResponse
import retrofit2.http.*

interface OrderApiService {

    @GET("orderDetail/getAllByCsId/{id}")
    suspend fun getOrderByCsId(@Path("id") customerId: Int): OrderResponse

    @GET("order/historyOrderByOdId/{id}")
    suspend fun getAllHistoryOrderByOdId(@Path("id") orderDetailId: Int): DetailOrderHistoryResponse

    @GET("orderDetail/getAllOD")
    suspend fun getAllOrderByAdmin(): OrderResponse

    @FormUrlEncoded
    @PUT("orderDetail/update/{odId}")
    suspend fun updateOrderDetailByAdmin(
        @Path("odId") odId: Int,
        @Field("od_status") orderDetailStatus: String
    ): UpdateStatusOrderDetailResponse

}