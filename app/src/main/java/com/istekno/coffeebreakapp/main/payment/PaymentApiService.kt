package com.istekno.coffeebreakapp.main.payment

import retrofit2.http.*

interface PaymentApiService {

    @GET("order/statusCart/{id}")
    suspend fun getCartByCustomerId(@Path("id") customerId: Int) : PaymentResponse

    @FormUrlEncoded
    @POST("orderDetail/Create")
    suspend fun createOrderDetail(
        @Field("csId") customerId: Int,
        @Field("odPaymentMethod") paymentMethod: String,
        @Field("odStatus") orderDetailStatus: String
    ) : PaymentResponse.GeneralResponse

    @POST("order/updateOdIdByCsId/{id}")
    suspend fun updateOrderDetailId(@Path("id") customerId: Int) : PaymentResponse.GeneralResponse

    @DELETE("delivery/deleteDeliveryByCsId/{id}")
    suspend fun deleteDelivery(@Path("id") customerId: Int) : PaymentResponse.GeneralResponse

}