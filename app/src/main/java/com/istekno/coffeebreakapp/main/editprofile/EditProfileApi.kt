package com.istekno.coffeebreakapp.main.editprofile

import com.istekno.coffeebreakapp.main.editprofile.model.AccountResponse
import com.istekno.coffeebreakapp.main.editprofile.model.CustomerResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface EditProfileApi {

    @FormUrlEncoded
    @PUT("account/update/{acId}")
    suspend fun updateAccount(
        @Path("acId") acId: Int,
        @Field("ac_email") acEmail: String,
        @Field("ac_name") acName: String,
        @Field("ac_phone") acPhone: String
        ): AccountResponse

    @Multipart
    @PUT("customer/{csId}")
    suspend fun updateCustomer(
        @Path("csId") csId: Int,
        @Part("cs_gender") csGender: RequestBody,
        @Part("cs_birthday") csBirthday: RequestBody,
        @Part("cs_address") csAddress: RequestBody? = null,
        @Part image: MultipartBody.Part? = null

    ): CustomerResponse

}