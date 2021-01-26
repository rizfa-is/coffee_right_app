package com.istekno.coffeebreakapp.main.login

import android.util.Log
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
                if (result.data?.accountLevel == 0) {
                    sharePref.setPreference(SharedPrefModel(result.data.accountId, result.data.customerId, result.data.accountLevel, result.data.token, true))
                } else if (result.data?.accountLevel == 1) {
                    sharePref.setPreference(SharedPrefModel(result.data.accountId, result.data.adminId, result.data.accountLevel, result.data.token, true))
                }

                Log.e("sharedPref", sharePref.getPreference().roleID.toString())
                isDataLogin.value = true
            }
        }
    }




}