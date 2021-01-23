package com.istekno.coffeebreakapp.main.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class SignupViewModel: ViewModel(), CoroutineScope {

    val isRegister = MutableLiveData<Boolean>()
    val isMessage = MutableLiveData<String>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    private lateinit var service: SignUpApiService

    fun setService(service: SignUpApiService) {
        this.service = service
    }

    fun callSignUpService(name: String, email: String, password: String, phoneNumber: String) {
        launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    service.registerRequest(name, email, phoneNumber, password, 0)
                } catch (e: Throwable) {
                    e.printStackTrace()

                    withContext(Dispatchers.Main) {
                        isRegister.value = false
                    }
                }
            }

            if (result is SignUpResponse) {
                if (result.success) {
                    isRegister.value = result.success
                    isMessage.value = result.message
                }
            } else {
                isRegister.value = false
                isMessage.value = "Email has been Registered"
            }
        }
    }
}