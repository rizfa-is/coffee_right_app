package com.istekno.coffeebreakapp.main.payment

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface PaymentApiService {

    @GET("order/statusCart/3")
    suspend fun getCartByCustomerId() : PaymentResponse

    @FormUrlEncoded
    @POST("orderDetail/Create")
    suspend fun createOrderDetail(
        @Field("csId") customerId: Int,
        @Field("odPaymentMethod") paymentMethod: String,
        @Field("odStatus") orderDetailStatus: String
    ) : PaymentResponse.CreateResponse

}