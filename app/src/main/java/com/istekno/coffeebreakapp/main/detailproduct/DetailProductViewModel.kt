package com.istekno.coffeebreakapp.main.detailproduct

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.istekno.coffeebreakapp.main.cart.CartResponse
import com.istekno.coffeebreakapp.main.payment.PaymentResponse
import com.istekno.coffeebreakapp.utilities.SharedPreferenceUtil
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class DetailProductViewModel : ViewModel(), CoroutineScope {

    val isLoading = MutableLiveData<Boolean>()
    val listData = MutableLiveData<List<DetailProductResponse.DataProduct>>()
    val isCreateSuccess = MutableLiveData<Boolean>()
    val isUpdateSuccess = MutableLiveData<Boolean>()
    val isCheckProduct = MutableLiveData<Boolean>()
    val orderId = MutableLiveData<Int>()
    val listCart = MutableLiveData<Int>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    private lateinit var service: DetailProductApiService
    private lateinit var sharePref: SharedPreferenceUtil

    fun setService(service: DetailProductApiService) {
        this.service = service
    }

    fun setSharePref(sharePref: SharedPreferenceUtil) {
        this.sharePref = sharePref
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

    fun createOrder(productId: Int, customerId: Int) {
        launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    service.createOrder(productId, customerId)
                } catch (e: Throwable) {
                    e.printStackTrace()

                    withContext(Dispatchers.Main) {
                        isCreateSuccess.value = false
                    }
                }
            }

            if (result is PaymentResponse.GeneralResponse) {
                isCreateSuccess.value = result.success
            }
        }
    }

    fun updateAmountOrderApi(orderId: Int) {
        launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    service.updateAmountOrder(orderId)
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
                    isUpdateSuccess.value = result.success
                }
            } else {
                isUpdateSuccess.value = false
            }
        }
    }

    fun checkProductOnCartApi(productId: Int, customerId: Int) {
        launch {
            val listProductHasBeenOrder = mutableListOf<Long>()
            val listOrderIdByCsId = mutableListOf<Long>()

            val result1 = withContext(Dispatchers.IO) {
                try {
                    service.getAllOrderByCsIdNCart(customerId)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (result1 is ListOrderByCsIdResponse) {
                result1.data.map {
                    if (it.csId == customerId) {
                        listProductHasBeenOrder.add(it.productId.toLong())
                        listOrderIdByCsId.add(it.orderId.toLong())
                    }
                }

                val list = result1.data.map {
                    ListOrderByCsIdResponse.DataOrder(
                        it.orderId,
                        it.productId,
                        it.csId,
                        it.orStatus
                    )
                }
                val mutableList = list.toMutableList()
                mutableList.removeIf { it.productId != productId }
                if (!mutableList.isNullOrEmpty()) {
                    orderId.value = mutableList[0].orderId
                }
            }

            if (listProductHasBeenOrder.map { it.toString() }.contains(productId.toString())) {
                isCheckProduct.value = true
            } else {
                isCheckProduct.value = false
            }

        }
    }

    fun getListCartByCsId() {
        launch {

            val result = withContext(Dispatchers.IO) {
                try {
                    service.getListCartByCsId(sharePref.getPreference().roleID!!)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (result is CartResponse) {
                val data = result.data.map {
                    CartResponse.DataCart(
                        it.orderId,
                        it.productId,
                        it.productName,
                        it.productImage,
                        it.customerId,
                        it.orderStatus,
                        it.orderAmount,
                        it.orderPrice,
                        it.orderCreated,
                        it.orderUpdated
                    )
                }
                listCart.value = data.size
            }
        }
    }

}