package com.istekno.coffeebreakapp.main.detailproduct

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class DetailProductViewModel: ViewModel(), CoroutineScope {

    val isLoading = MutableLiveData<Boolean>()
    val listData = MutableLiveData<List<DetailProductResponse.DataProduct>>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    private lateinit var service: DetailProductApiService

    fun setService(service: DetailProductApiService) {
        this.service = service
    }

    fun getProductDetail(productId: Int) {
        launch {
            isLoading.value = true
            val result = withContext(Dispatchers.IO) {
                try {
                    service.getDetailProduct(productId)
                } catch (e: Throwable) {
                    e.printStackTrace()

                    withContext(Dispatchers.Main) {
                        isLoading.value = false
                    }
                }
            }

            if (result is DetailProductResponse) {
                if (result.success) {
                    listData.value = result.data
                    isLoading.value = false
                } else {
                    isLoading.value = true
                }
            }
        }
    }
}