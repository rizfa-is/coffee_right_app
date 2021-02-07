package com.istekno.coffeebreakapp.main.editpassword

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.istekno.coffeebreakapp.main.editprofile.EditProfileApi
import com.istekno.coffeebreakapp.main.editprofile.EditProfileResponse
import kotlinx.coroutines.*
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

class EditPasswordViewModel : ViewModel(), CoroutineScope {
    private lateinit var service: EditPasswordApi

    val onSuccessLiveData = MutableLiveData<Boolean>()
    val onSuccessLiveDataPassword = MutableLiveData<Boolean>()
    val onMessageLiveData = MutableLiveData<String>()
    val onFailLiveData = MutableLiveData<String>()
    val isLoading = MutableLiveData<Boolean>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setService(serviceEditPassword: EditPasswordApi) {
        this@EditPasswordViewModel.service = serviceEditPassword
    }

    fun checkingPassword(
        acId: Int,
        acPassword: String
    ) {
        launch {
            isLoading.value = true
            val response = withContext(Dispatchers.IO) {
                try {
                    service.checkPassword(
                        acId = acId,
                        acPassword = acPassword
                    )
                } catch (e: HttpException) {
                    withContext(Dispatchers.Main) {
                        onSuccessLiveData.value = false

                        when {
                            e.code() == 400 -> {
                                onFailLiveData.value = "Wrong current password!"
                            }
                            e.code() == 404 -> {
                                onFailLiveData.value = "Image to large!"
                            }
                            else -> {
                                onFailLiveData.value = "Internal Server Error!"
                            }
                        }
                    }
                }
            }
            if (response is VerifyPasswordResponse) {
                isLoading.value = false

                if (response.success) {
                    onSuccessLiveData.value = true
                    onMessageLiveData.value = response.message
                } else {
                    onFailLiveData.value = response.message
                }
            }

        }
    }

    fun changingPassword(
        acId: Int,
        acPassword: String
    ) {
        launch {
            isLoading.value = true
            val response = withContext(Dispatchers.IO) {
                try {
                    service.changePassword(
                        acId = acId,
                        acPassword = acPassword
                    )
                } catch (e: HttpException) {
                    withContext(Dispatchers.Main) {
                        onSuccessLiveDataPassword.value = false

                        when {
                            e.code() == 400 -> {
                                onFailLiveData.value = "Fail to add data!"
                            }
                            e.code() == 404 -> {
                                onFailLiveData.value = "Image to large!"
                            }
                            else -> {
                                onFailLiveData.value = "Internal Server Error!"
                            }
                        }
                    }
                }
            }
            if (response is VerifyPasswordResponse) {
                isLoading.value = false

                if (response.success) {
                    onSuccessLiveDataPassword.value = true
                    onMessageLiveData.value = response.message
                } else {
                    onFailLiveData.value = response.message
                }
            }

        }
    }
}