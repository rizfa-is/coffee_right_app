package com.istekno.coffeebreakapp.main.forgotpassword.checkemail

import retrofit2.http.*

interface CheckEmailApi {

    @FormUrlEncoded
    @POST("account/email/check")
    suspend fun checkEmail(
        @Field("email") email: String
    ): VerifyEmailResponse

}