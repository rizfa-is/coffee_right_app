package com.istekno.coffeebreakapp.main.maincontent.order.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.istekno.coffeebreakapp.main.orderhistory.OrderHistoryApiService
import com.istekno.coffeebreakapp.main.orderhistory.detail.DetailOrderHistoryModel
import com.istekno.coffeebreakapp.main.orderhistory.detail.DetailOrderHistoryResponse
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class DetailOrderViewModel: ViewModel(), CoroutineScope {

    val listData = MutableLiveData<List<DetailOrderHistoryModel>>()
    val isGetList = MutableLiveData<Boolean>()
    val isLoading = MutableLiveData<Boolean>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    private lateinit var service: OrderHistoryApiService

    fun setService(service: OrderHistoryApiService) {
        this.service = service
    }

    fun callOrderApiService(orderDetailId: Int) {
        launch {
            isLoading.value = true

            val result = withContext(Dispatchers.IO) {
                try {
                    service.getAllHistoryOrderByOdId(orderDetailId)
                } catch (e: Throwable) {
                    e.printStackTrace()

                    withContext(Dispatchers.Main) {
                        isGetList.value = false
                        isLoading.value = false
                    }
                }
            }

            if (result is DetailOrderHistoryResponse) {
                if (result.success) {
                    isGetList.value = result.success
                    val list = result.data.map {
                        DetailOrderHistoryModel(it.productName, it.orderPrice, it.orderDetailStatus, it.orderAmount)
                    }
                    listData.value = list
                    isLoading.value = false
                } else {
                    isGetList.value = false
                    isLoading.value = false
                }
            } else {
                isGetList.value = false
                isLoading.value = false
            }
        }
    }
}