package com.istekno.coffeebreakapp.main.maincontent.maincontent

import com.istekno.coffeebreakapp.main.maincontent.profile.ProfileResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MainContentService {

    @GET("customer/detail/{id}")
    suspend fun getCustomerByID(@Path("id") id: Int): MainContentResponse
}