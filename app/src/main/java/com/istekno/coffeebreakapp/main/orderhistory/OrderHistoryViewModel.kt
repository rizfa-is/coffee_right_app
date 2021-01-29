package com.istekno.coffeebreakapp.main.orderhistory

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class OrderHistoryViewModel: ViewModel(), CoroutineScope {

    val listData = MutableLiveData<List<OrderHistoryModel>>()
    val isLoading = MutableLiveData<Boolean>()
    val isNotFound = MutableLiveData<Boolean>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    private lateinit var service: OrderHistoryApiService

    fun setService(service: OrderHistoryApiService) {
        this.service = service
    }

    fun callOrderHistoryApi(customerId: Int) {
        launch {
            isLoading.value = true

            val result = withContext(Dispatchers.IO) {
                try {
                    service.getOrderHistoryByCsId(customerId)
                } catch (e: Throwable) {
                    e.printStackTrace()

                    withContext(Dispatchers.Main) {
                        isLoading.value = false
                        isNotFound.value = true
                    }
                }
            }

            if (result is OrderHistoryResponse) {
                if (result.success) {
                    val list = result.data.map {
                        OrderHistoryModel(it.orderId, it.customerId, it.deliveryId, it.priceBeforeTax, it.couponId, it.totalPrice, it.orderStatus,
                            it.orderPayment, it.orderTax, it.orderCreated, it.orderUpdated)
                    }
                    val mutable = list.toMutableList()
                    mutable.removeAll { it.orderStatus != "Done" }
                    listData.value = mutable
                    isLoading.value = false
                } else {
                    isNotFound.value = true
                }
            }
        }
    }
}