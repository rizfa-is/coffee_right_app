package com.istekno.coffeebreakapp.main.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.istekno.coffeebreakapp.main.maincontent.homepage.HomeResponse
import com.istekno.coffeebreakapp.main.maincontent.homepage.HomeService
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class FavoriteViewModel: ViewModel(), CoroutineScope {

    val isLoading = MutableLiveData<Boolean>()
    val listData = MutableLiveData<List<HomeResponse.DataProduct>>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    private lateinit var service: HomeService

    fun setService(service: HomeService) {
        this.service = service
    }

    fun getAllProduct() {
        launch {

            isLoading.value = true
            val result = withContext(Job() + Dispatchers.IO) {
                try {
                    service.getAllProduct()
                } catch (e: Throwable) {
                    e.printStackTrace()

                    withContext(Job() + Dispatchers.Main) {
                        isLoading.value = false
                    }
                }
            }

            if (result is HomeResponse) {
                val data = result.data.map {
                    HomeResponse.DataProduct(it.productId, it.discountId, it.productName, it.productDesc, it.productPrice, it.productImage, it.productFavorite, it.productCategory, it.productCreated, it.productUpdated)
                }

                listData.value = data
                isLoading.value = false
            }
        }
    }
}