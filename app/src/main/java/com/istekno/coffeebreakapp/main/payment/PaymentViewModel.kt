package com.istekno.coffeebreakapp.main.payment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class PaymentViewModel : ViewModel(), CoroutineScope {

    val listData = MutableLiveData<List<PaymentModel>>()
    val isLoading = MutableLiveData<Boolean>()
    val totalPrice = MutableLiveData<Int>()
    val isProcessSuccess = MutableLiveData<Boolean>()
    val isDeleteSuccess = MutableLiveData<Boolean>()
    val isUpdateSuccess = MutableLiveData<Boolean>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    private lateinit var service: PaymentApiService

    fun setService(service: PaymentApiService) {
        this.service = service
    }

    fun callApiService(customerId: Int) {
        launch {
            isLoading.value = true

            val result = withContext(Dispatchers.IO) {
                try {
                    service.getCartByCustomerId(customerId)
                } catch (e: Throwable) {
                    e.printStackTrace()

                    withContext(Dispatchers.Main) {
                        isLoading.value = false
                    }
                }
            }

            if (result is PaymentResponse) {
                if (result.success) {
                    val list = result.data.map {
                        PaymentModel(it.amount, it.price, it.productName)
                    }
                    val price = result.data.map {
                        PriceModel(it.price)
                    }
                    val mutable = price.toMutableList()
                    val listPrice = mutableListOf<Int>()
                    mutable.map { listPrice.add(it.price) }
                    var total = 0
                    for (i in 0 until listPrice.size) {
                        total += listPrice[i]
                    }
                    totalPrice.value = total
                    listData.value = list
                    isLoading.value = false
                }
            }
        }
    }

    fun createOrderDetailApi(customerId: Int, paymentMethod: String, status: String) {
        launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    service.createOrderDetail(customerId, paymentMethod, status)
                } catch (e: Throwable) {
                    e.printStackTrace()

                    withContext(Dispatchers.Main) {
                        isProcessSuccess.value = false
                    }
                }
            }

            if (result is PaymentResponse.GeneralResponse) {
                if (result.success) {
                    isProcessSuccess.value = result.success
                } else {
                    isProcessSuccess.value = false
                }
            } else {
                isProcessSuccess.value = false
            }
        }
    }

    fun updateOrderDetailId(customerId: Int) {
        launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    service.updateOrderDetailId(customerId)
                } catch (e: Throwable) {
                    e.printStackTrace()

                    withContext(Dispatchers.Main) {
                        isUpdateSuccess.value = false
                    }
                }
            }

            if (result is PaymentResponse.GeneralResponse) {
                if (result.success) {
                    isUpdateSuccess.value = result.success
                } else {
                    isUpdateSuccess.value = false
                }
            } else {
                isUpdateSuccess.value = false
            }
        }
    }

    fun deleteDelivery(customerId: Int) {
        launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    service.deleteDelivery(customerId)
                } catch (e: Throwable) {
                    e.printStackTrace()

                    withContext(Dispatchers.Main) {
                        isDeleteSuccess.value = false
                    }
                }
            }

            if (result is PaymentResponse.GeneralResponse) {
                if (result.success) {
                    isDeleteSuccess.value = result.success
                }
            }
        }
    }
}