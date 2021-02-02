package com.istekno.coffeebreakapp.main.maincontent.orderhistory

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class OrderHistoryViewModel : ViewModel(), CoroutineScope {

    val listData = MutableLiveData<List<OrderHistoryModel>>()
    val isLoading = MutableLiveData<Boolean>()
    val isGetList = MutableLiveData<Boolean>()

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
                        isGetList.value = false
                    }
                }
            }

            if (result is OrderHistoryResponse) {
                if (result.success) {
                    val list = result.data.map {
                        OrderHistoryModel(
                            it.orderId,
                            it.customerId,
                            it.deliveryId,
                            it.priceBeforeTax,
                            it.couponId,
                            it.totalPrice,
                            it.orderStatus,
                            it.orderPayment,
                            it.orderTax,
                            it.orderCreated,
                            it.orderUpdated
                        )
                    }
                    val mutable = list.toMutableList()
                    mutable.removeIf { it.orderStatus != "Done" }
                    if (!mutable.isNullOrEmpty()) {
                        isGetList.value = result.success
                        listData.value = mutable
                    } else {
                        isGetList.value = false
                    }
                    isLoading.value = false
                } else {
                    isGetList.value = false
                }
            }

//            isGetList.value = false
//            isLoading.value = false
        }
    }

    fun callOrderHistoryByAdminApi() {
        launch {
            isLoading.value = true

            val result = withContext(Dispatchers.IO) {
                try {
                    service.getAllOrderByAdmin()
                } catch (e: Throwable) {
                    e.printStackTrace()

                    withContext(Dispatchers.Main) {
                        isLoading.value = false
                        isGetList.value = false
                    }
                }
            }

            if (result is OrderHistoryResponse) {
                if (result.success) {
                    val list = result.data.map {
                        OrderHistoryModel(
                            it.orderId,
                            it.customerId,
                            it.deliveryId,
                            it.priceBeforeTax,
                            it.couponId,
                            it.totalPrice,
                            it.orderStatus,
                            it.orderPayment,
                            it.orderTax,
                            it.orderCreated,
                            it.orderUpdated
                        )
                    }
                    val mutable = list.toMutableList()
                    mutable.removeIf { it.orderStatus != "Done" }
                    if (!mutable.isNullOrEmpty()) {
                        isGetList.value = result.success
                        listData.value = mutable
                    } else {
                        isGetList.value = false
                    }
                    isLoading.value = false
                } else {
                    isGetList.value = false
                }
            }
        }
    }

}