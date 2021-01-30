package com.istekno.coffeebreakapp.main.forgotpassword.checkemail

import com.istekno.coffeebreakapp.main.editprofile.model.AccountResponse
import com.istekno.coffeebreakapp.main.editprofile.model.CustomerResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface CheckEmailApi {

    @FormUrlEncoded
    @POST("account/email/check")
    suspend fun checkEmail(
        @Field("email") email: String
    ): VerifyEmailResponse

}