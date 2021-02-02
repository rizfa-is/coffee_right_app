package com.istekno.coffeebreakapp.main.splashscreen

import com.istekno.coffeebreakapp.main.payment.PaymentResponse
import retrofit2.http.GET

interface CheckJwtExpiredApiService {

    @GET("customer")
    suspend fun checkJwt() : PaymentResponse.GeneralResponse

}