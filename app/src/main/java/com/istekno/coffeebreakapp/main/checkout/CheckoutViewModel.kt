package com.istekno.coffeebreakapp.main.checkout

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.istekno.coffeebreakapp.utilities.SharedPreferenceUtil
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class CheckoutViewModel : ViewModel(), CoroutineScope {

    val isError = MutableLiveData<Boolean>()
    val isMessage = MutableLiveData<String>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    private lateinit var service: CheckoutApiService
    private lateinit var sharedPref: SharedPreferenceUtil

    fun setService(service: CheckoutApiService) {
        this.service = service
    }

    fun setSharedPref(sharedPreferenceUtil: SharedPreferenceUtil) {
        this.sharedPref = sharedPreferenceUtil
    }

    fun addDelivery(deliveryMethod: String, deliveryNow: String, deliverySetTime: String) {
        launch {

            val result = withContext(Job() + Dispatchers.IO) {
                try {
                    service.addDelivery(
                        sharedPref.getPreference().roleID!!,
                        deliveryMethod,
                        deliveryNow,
                        deliverySetTime,
                        sharedPref.getPreference().acAddress!!
                    )
                } catch (e: Throwable) {
                    e.printStackTrace()

                    withContext(Job() + Dispatchers.Main) {
                        isError.value = true
                        isMessage.value = e.message
                    }
                }
            }

            if (result is CheckoutResponse) {
                isError.value = true
                isMessage.value = result.message
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Job().cancel()
    }
}