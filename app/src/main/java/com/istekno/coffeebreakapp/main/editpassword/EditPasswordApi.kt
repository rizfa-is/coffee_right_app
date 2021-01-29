package com.istekno.coffeebreakapp.main.editpassword

import retrofit2.http.*

interface EditPasswordApi {

    @FormUrlEncoded
    @POST("account/password/check/{acId}")
    suspend fun checkPassword(
        @Path("acId") acId: Int,
        @Field("acPassword") acPassword: String
    ): VerifyPasswordResponse

    @FormUrlEncoded
    @PUT("account/password/update/{acId}")
    suspend fun changePassword(
        @Path("acId") acId: Int,
        @Field("ac_password") acPassword: String
    ): VerifyPasswordResponse
}