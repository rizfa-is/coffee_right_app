package com.istekno.coffeebreakapp.main.payment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.istekno.coffeebreakapp.main.orderhistory.detail.DetailOrderHistoryModel
import com.istekno.coffeebreakapp.main.orderhistory.detail.DetailOrderHistoryResponse
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class PaymentViewModel : ViewModel(), CoroutineScope {

    val listData = MutableLiveData<List<PaymentModel>>()
    val isLoading = MutableLiveData<Boolean>()
    val totalPrice = MutableLiveData<Int>()
    val isCreateOrderDetail = MutableLiveData<Boolean>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    private lateinit var service: PaymentApiService

    fun setService(service: PaymentApiService) {
        this.service = service
    }

    fun callApiService() {
        launch {
            isLoading.value = true

            val result = withContext(Dispatchers.IO) {
                try {
                    service.getCartByCustomerId()
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
                        isCreateOrderDetail.value = false
                    }
                }
            }

            if (result is PaymentResponse.CreateResponse) {
                if (result.success) {
                    isCreateOrderDetail.value = result.success
                }
            }
        }
    }
}