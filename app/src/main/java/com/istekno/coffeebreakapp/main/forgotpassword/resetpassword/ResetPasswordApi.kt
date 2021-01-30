package com.istekno.coffeebreakapp.main.forgotpassword.resetpassword

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.PUT
import retrofit2.http.Path

interface ResetPasswordApi {

    @FormUrlEncoded
    @PUT("account/password/update/{acId}")
    suspend fun resetPassword(
        @Path("acId") acId: Int,
        @Field("ac_password") acPassword: String
    ): ResetPasswordResponse

}