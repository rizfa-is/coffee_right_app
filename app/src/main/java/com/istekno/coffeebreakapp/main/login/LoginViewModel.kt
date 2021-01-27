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
                val data = result.data

                if (data?.accountLevel == 0) {

                    sharePref.setPreference(SharedPrefModel(data.accountId, data.accountEmail, data.accountName, data.customerImage,
                        data.customerId, data.accountLevel, data.token, true))

                } else if (data?.accountLevel == 1) {

                    sharePref.setPreference(SharedPrefModel(data.accountId, data.accountEmail, data.accountName, data.adminImage,
                        data.adminId, data.accountLevel, data.token, true))
                }

                isDataLogin.value = true
            }
        }
    }




}