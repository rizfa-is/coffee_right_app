package com.istekno.coffeebreakapp.main.editprofile

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.PUT
import retrofit2.http.Path

interface AccountApi {

    @FormUrlEncoded
    @PUT("account/{acId}")
    suspend fun updateAccount(
        @Path("acId") acId: Int,
        @Field("ac_name") acName: String,
        @Field("ac_phone") acPhone: String
        ): AccountResponse

    @FormUrlEncoded
    @PUT("account/password/{acId}")
    suspend fun resetPassword(
        @Path("acId") acId: Int,
        @Field("ac_password") acPassword: String
    ): AccountResponse

}