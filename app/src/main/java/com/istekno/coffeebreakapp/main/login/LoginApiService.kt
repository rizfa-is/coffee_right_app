package com.istekno.coffeebreakapp.main.login

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginApiService {

    @FormUrlEncoded
    @POST("account/login")
    suspend fun loginRequest(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

}