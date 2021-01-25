package com.istekno.coffeebreakapp.main.maincontent.homepage

import retrofit2.http.GET

interface HomeService {

    @GET
    suspend fun getAllProduct(): ProductResponse
}