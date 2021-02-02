package com.istekno.coffeebreakapp.main.maincontent.mainactivity

import com.istekno.coffeebreakapp.main.maincontent.homepage.GetProductResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MainContentService {

    @GET("customer/detail/{id}")
    suspend fun getCustomerByID(@Path("id") id: Int): MainContentResponse

    @GET("product/getAllProduct")
    suspend fun getProductByQuery(
        @Query("search") search: String
    ): GetProductResponse

    @GET("product/getFilterProduct")
    suspend fun getProductFilter(
        @Query("filter") filter: String
    ): GetProductResponse
}