package com.istekno.coffeebreakapp.main.splashscreen

import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.istekno.coffeebreakapp.main.payment.PaymentResponse
import com.istekno.coffeebreakapp.utilities.SharedPreferenceUtil
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class SplashScreenViewModel : ViewModel(), CoroutineScope {

    val isRemember = MutableLiveData<Boolean>()
    val checkJwt = MutableLiveData<Boolean>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    private lateinit var sharePref: SharedPreferenceUtil
    private lateinit var service: CheckJwtExpiredApiService

    fun setService(service: CheckJwtExpiredApiService) {
        this.service = service
    }

    fun setSharePref(sharePref: SharedPreferenceUtil) {
        this.sharePref = sharePref
    }

    fun checkLoginStatus() {
        isRemember.value = sharePref.getPreference().isLogin!!
    }

    fun callApiService() {
        launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    service.checkJwt()
                } catch (e: Throwable) {
                    e.printStackTrace()

                    withContext(Dispatchers.Main) {
                        checkJwt.value = false
                    }
                }
            }

            if (result is PaymentResponse.GeneralResponse) {
                checkJwt.value = result.success
            }
        }
    }

}