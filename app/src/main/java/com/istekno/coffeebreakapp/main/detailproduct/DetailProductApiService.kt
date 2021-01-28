package com.istekno.coffeebreakapp.main.detailproduct

import com.istekno.coffeebreakapp.main.payment.PaymentResponse
import retrofit2.http.*

interface DetailProductApiService {

    @GET("product/getProductByPrId/{id}")
    suspend fun getDetailProduct(@Path("id") productId: Int) : DetailProductResponse

    @FormUrlEncoded
    @POST("order/addOrder")
    suspend fun createOrder(@Field("prId") productId: Int,
                            @Field("csId") customerId: Int) : PaymentResponse.GeneralResponse

}