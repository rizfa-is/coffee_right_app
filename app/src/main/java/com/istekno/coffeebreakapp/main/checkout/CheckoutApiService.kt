package com.istekno.coffeebreakapp.main.checkout

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface CheckoutApiService {

    @FormUrlEncoded
    @POST("delivery/create")
    suspend fun addDelivery(
        @Field("cs_id") csID: Int,
        @Field("dv_dt") deliveryMethod: String,
        @Field("dv_yn") deliveryNow: String,
        @Field("dv_st") deliverySetTime: String,
        @Field("dv_address") deliveryAddress: String
    ): CheckoutResponse

}