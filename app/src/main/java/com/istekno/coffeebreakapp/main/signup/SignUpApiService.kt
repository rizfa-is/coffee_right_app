package com.istekno.coffeebreakapp.main.signup

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface SignUpApiService {

    @FormUrlEncoded
    @POST("account")
    suspend fun registerRequest(
            @Field("ac_name") accountName: String,
            @Field("ac_email") accountEmail: String,
            @Field("ac_phone") accountPhone: String,
            @Field("ac_password") accountPassword: String,
            @Field("ac_level") accountLevel: Int
    ) : SignUpResponse

}