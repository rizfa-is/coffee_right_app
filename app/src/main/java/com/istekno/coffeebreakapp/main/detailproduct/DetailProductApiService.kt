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

    @PUT("order/updateOrder/{orderId}")
    suspend fun updateAmountOrder(@Path("orderId") orderId : Int) : PaymentResponse.GeneralResponse

    @GET("order/statusCart/{csId}")
    suspend fun getAllOrderByCsIdNCart(@Path("csId") csId : Int) : ListOrderByCsIdResponse

}