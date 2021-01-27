package com.istekno.coffeebreakapp.main.editprofile

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface CustomerApi {

    @Multipart
    @PUT("customer/{csId}")
    suspend fun updateCustomer(
        @Path("csId") csId: Int,
        @Part("cs_gender") csGender: RequestBody,
        @Part("cs_birthday") csBirthday: RequestBody,
        @Part("cs_address") csAddress: RequestBody,
        @Part image: MultipartBody.Part? = null

    ): CustomerResponse

}