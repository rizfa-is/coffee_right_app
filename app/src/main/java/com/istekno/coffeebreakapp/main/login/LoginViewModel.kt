package com.istekno.coffeebreakapp.main.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.istekno.coffeebreakapp.model.SharedPrefModel
import com.istekno.coffeebreakapp.utilities.SharedPreferenceUtil
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class LoginViewModel: ViewModel(), CoroutineScope {

    val isDataLogin = MutableLiveData<Boolean>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    private lateinit var service: LoginApiService
    private lateinit var sharePref: SharedPreferenceUtil

    fun setService(service: LoginApiService) {
        this.service = service
    }

    fun setSharePref(sharePref: SharedPreferenceUtil) {
        this.sharePref = sharePref
    }

    fun callLoginApi(email: String, password: String) {
        launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    service.loginRequest(email, password)
                } catch (e: Throwable) {
                    e.printStackTrace()

                    withContext(Dispatchers.Main) {
                        isDataLogin.value = false
                    }
                }
            }

            if (result is LoginResponse) {
                if (result.success) {
                    sharePref.setPreference(SharedPrefModel(result.data?.accountId, result.data?.accountLevel, result.data?.Token, true))
                    isDataLogin.value = true
                } else {
                    isDataLogin.value =false
                }
            }
        }
    }




}