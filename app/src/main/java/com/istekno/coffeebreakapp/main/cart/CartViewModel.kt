package com.istekno.coffeebreakapp.main.cart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.istekno.coffeebreakapp.utilities.SharedPreferenceUtil
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class CartViewModel : ViewModel(), CoroutineScope {

    val listCart = MutableLiveData<List<CartResponse.DataCart>>()
    val isGetListCart = MutableLiveData<Boolean>()
    val isUpdateCart = MutableLiveData<Boolean>()
    val isMessage = MutableLiveData<String>()
    val isLoading = MutableLiveData<Boolean>()
    val totalPriceCart = MutableLiveData<String>()
    val isDeleteSuccess = MutableLiveData<Boolean>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    private lateinit var service: CartApiService
    private lateinit var sharedPref: SharedPreferenceUtil

    fun setService(service: CartApiService) {
        this.service = service
    }

    fun setSharedPref(sharedPref: SharedPreferenceUtil) {
        this.sharedPref = sharedPref
    }

    fun getListCartByCsId() {
        launch {
            isLoading.value = true

            val result = withContext(Dispatchers.IO) {
                try {
                    service.getListCartByCsId(sharedPref.getPreference().roleID!!)
                } catch (e: Throwable) {
                    e.printStackTrace()

                    withContext(Dispatchers.Main) {
                        isLoading.value = false
                        isGetListCart.value = false
                    }
                }
            }

            if (result is CartResponse) {
                if (result.success) {
                    isGetListCart.value = true
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
                    listCart.value = data
                    isLoading.value = false
                } else {
                    isGetListCart.value = false
                    isLoading.value = false
                }
            }
        }
    }

    fun getListPriceCartByCsId() {
        launch {
            isLoading.value = true

            val result = withContext(Dispatchers.IO) {
                try {
                    service.getCartPriceByCsId(sharedPref.getPreference().roleID!!)
                } catch (e: Throwable) {
                    e.printStackTrace()

                    withContext(Dispatchers.Main) {
                        isLoading.value = false
                        isGetListCart.value = false
                    }
                }
            }

            if (result is CartPriceResponse) {
                if (result.success) {
                    isGetListCart.value = true
                    val dataPrice = result.data.map {
                        CartPriceResponse.DataPriceCart(it.orderPrice)
                    }
                    val mutable = dataPrice.toMutableList()
                    val listPrice = mutableListOf<Int>()
                    mutable.map {
                        listPrice.add(it.orderPrice)
                    }
                    var total = 0
                    for (i in 0 until listPrice.size) {
                        total += listPrice[i]
                    }
                    totalPriceCart.value = total.toString()
                    isLoading.value = false
                } else {
                    isGetListCart.value = false
                    isLoading.value = false
                }
            }

        }
    }

    fun updatePlusCartByOrId(orId: Int) {
        launch {
            isLoading.value = true

            val result1 = withContext(Dispatchers.IO) {
                try {
                    service.updateOrderPlus(orId)
                } catch (e: Throwable) {
                    e.printStackTrace()

                    withContext(Dispatchers.Main) {
                        isLoading.value = false
                        isUpdateCart.value = false
                    }
                }
            }

            if (result1 is UpdateOrderResponse) {
                if (result1.success) {
                    isUpdateCart.value = result1.success
                    isLoading.value = false
                } else {
                    isUpdateCart.value = false
                    isLoading.value = false
                    isMessage.value = result1.message
                }
            } else {
                isUpdateCart.value = false
                isLoading.value = false
                isMessage.value = "Something wrong..."
            }

            val result2 = withContext(Dispatchers.IO) {
                try {
                    service.getListCartByCsId(sharedPref.getPreference().roleID!!)
                } catch (e: Throwable) {
                    e.printStackTrace()

                    withContext(Dispatchers.Main) {
                        isLoading.value = false
                        isGetListCart.value = false
                    }
                }
            }

            if (result2 is CartResponse) {
                if (result2.success) {
                    isGetListCart.value = true
                    val data = result2.data.map {
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
                    listCart.value = data
                    isLoading.value = false
                } else {
                    isGetListCart.value = false
                    isLoading.value = false
                }
            }

            val result3 = withContext(Dispatchers.IO) {
                try {
                    service.getCartPriceByCsId(sharedPref.getPreference().roleID!!)
                } catch (e: Throwable) {
                    e.printStackTrace()

                    withContext(Dispatchers.Main) {
                        isLoading.value = false
                        isGetListCart.value = false
                    }
                }
            }

            if (result3 is CartPriceResponse) {
                if (result3.success) {
                    isGetListCart.value = true
                    val dataPrice = result3.data.map {
                        CartPriceResponse.DataPriceCart(it.orderPrice)
                    }
                    val mutable = dataPrice.toMutableList()
                    val listPrice = mutableListOf<Int>()
                    mutable.map {
                        listPrice.add(it.orderPrice)
                    }
                    var total = 0
                    for (i in 0 until listPrice.size) {
                        total += listPrice[i]
                    }
                    totalPriceCart.value = total.toString()
                    isLoading.value = false
                } else {
                    isGetListCart.value = false
                    isLoading.value = false
                }
            }

        }
    }

    fun updateMinusCartByOrId(orId: Int) {
        launch {
            isLoading.value = true

            val result1 = withContext(Dispatchers.IO) {
                try {
                    service.updateOrderMin(orId)
                } catch (e: Throwable) {
                    e.printStackTrace()

                    withContext(Dispatchers.Main) {
                        isLoading.value = false
                        isUpdateCart.value = false
                    }
                }
            }

            if (result1 is UpdateOrderResponse) {
                if (result1.success) {
                    isUpdateCart.value = result1.success
                    isLoading.value = false
                } else {
                    isUpdateCart.value = false
                    isLoading.value = false
                    isMessage.value = result1.message
                }
            } else {
                isUpdateCart.value = false
                isLoading.value = false
                isMessage.value = "Something wrong..."
            }

            val result2 = withContext(Dispatchers.IO) {
                try {
                    service.getListCartByCsId(sharedPref.getPreference().roleID!!)
                } catch (e: Throwable) {
                    e.printStackTrace()

                    withContext(Dispatchers.Main) {
                        isLoading.value = false
                        isGetListCart.value = false
                    }
                }
            }

            if (result2 is CartResponse) {
                if (result2.success) {
                    isGetListCart.value = true
                    val data = result2.data.map {
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
                    listCart.value = data
                    isLoading.value = false
                } else {
                    isGetListCart.value = false
                    isLoading.value = false
                }
            }

            val result3 = withContext(Dispatchers.IO) {
                try {
                    service.getCartPriceByCsId(sharedPref.getPreference().roleID!!)
                } catch (e: Throwable) {
                    e.printStackTrace()

                    withContext(Dispatchers.Main) {
                        isLoading.value = false
                        isGetListCart.value = false
                    }
                }
            }

            if (result3 is CartPriceResponse) {
                if (result3.success) {
                    isGetListCart.value = true
                    val dataPrice = result3.data.map {
                        CartPriceResponse.DataPriceCart(it.orderPrice)
                    }
                    val mutable = dataPrice.toMutableList()
                    val listPrice = mutableListOf<Int>()
                    mutable.map {
                        listPrice.add(it.orderPrice)
                    }
                    var total = 0
                    for (i in 0 until listPrice.size) {
                        total += listPrice[i]
                    }
                    totalPriceCart.value = total.toString()
                    isLoading.value = false
                } else {
                    isGetListCart.value = false
                    isLoading.value = false
                }
            }

        }
    }

    fun deleteOrderById(orId: Int) {
        launch {
            isLoading.value = true

            val result = withContext(Dispatchers.IO) {
                try {
                    service.deleteOrderById(orId)
                } catch (e: Throwable) {
                    e.printStackTrace()

                    withContext(Dispatchers.Main) {
                        isLoading.value = false
                        isDeleteSuccess.value = false
                    }
                }
            }

            if (result is UpdateOrderResponse) {
                if (result.success) {
                    isDeleteSuccess.value = result.success
                    isLoading.value = false
                    isMessage.value = result.message
                } else {
                    isDeleteSuccess.value = false
                    isLoading.value = false
                    isMessage.value = result.message
                }
            } else {
                isDeleteSuccess.value = false
                isLoading.value = false
                isMessage.value = "Something wrong..."
            }

        }
    }

}