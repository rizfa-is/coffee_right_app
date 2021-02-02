package com.istekno.coffeebreakapp.main.promo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.istekno.coffeebreakapp.main.maincontent.homepage.GetProductResponse
import com.istekno.coffeebreakapp.main.maincontent.homepage.HomeService
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class PromoViewModel: ViewModel(), CoroutineScope {
    val isLoading = MutableLiveData<Boolean>()
    val listData = MutableLiveData<List<GetProductResponse.DataProduct>>()

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

            if (result is GetProductResponse) {
                val data = result.data.map {
                    GetProductResponse.DataProduct(it.productId, it.discountId, it.productName, it.productDesc, it.productPrice, it.productImage, it.productFavorite, it.productCategory, it.productCreated, it.productUpdated)
                }

                listData.value = data
                isLoading.value = false
            }
        }
    }
}