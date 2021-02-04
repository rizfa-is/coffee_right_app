package com.istekno.coffeebreakapp.main.maincontent.order.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.istekno.coffeebreakapp.main.maincontent.order.OrderApiService
import com.istekno.coffeebreakapp.main.maincontent.orderhistory.OrderHistoryResponse
import com.istekno.coffeebreakapp.main.maincontent.orderhistory.detail.DetailOrderHistoryModel
import com.istekno.coffeebreakapp.main.maincontent.orderhistory.detail.DetailOrderHistoryResponse
import com.istekno.coffeebreakapp.main.maincontent.orderhistory.detail.UpdateStatusOrderDetailResponse
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class DetailOrderViewModel : ViewModel(), CoroutineScope {

    val listData = MutableLiveData<List<DetailOrderHistoryModel>>()
    val isGetList = MutableLiveData<Boolean>()
    val isLoading = MutableLiveData<Boolean>()
    val isUpdate = MutableLiveData<Boolean>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    private lateinit var service: OrderApiService

    fun setService(service: OrderApiService) {
        this.service = service
    }

    fun updateStatusByAdmin(odId: Int) {
        launch {
            isLoading.value = true

            val result = withContext(Dispatchers.IO) {
                try {
                    service.updateOrderDetailByAdmin(odId, "Done")
                } catch (e: Throwable) {
                    e.printStackTrace()

                    withContext(Dispatchers.Main) {
                        isUpdate.value = false
                        isLoading.value = false
                    }
                }
            }

            if (result is UpdateStatusOrderDetailResponse) {
                if (result.success) {
                    isUpdate.value = result.success
                    isLoading.value = false
                } else {
                    isUpdate.value = false
                    isLoading.value = false
                }
            } else {
                isUpdate.value = false
                isLoading.value = false
            }
        }
    }

}