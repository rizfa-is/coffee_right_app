package com.istekno.coffeebreakapp.main.detailproduct

import retrofit2.http.GET
import retrofit2.http.Path

interface DetailProductApiService {

    @GET("product/getProductByPrId/{id}")
    suspend fun getDetailProduct(@Path("id") productId: Int) : DetailProductResponse

}