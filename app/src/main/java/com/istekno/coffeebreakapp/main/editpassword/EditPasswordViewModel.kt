package com.istekno.coffeebreakapp.main.editpassword

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.istekno.coffeebreakapp.main.editprofile.EditProfileApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

class EditPasswordViewModel: ViewModel(), CoroutineScope {
    private lateinit var service: EditPasswordApi

    val onSuccessLiveData = MutableLiveData<Boolean>()
    val onSuccessLiveDataPassword = MutableLiveData<Boolean>()

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
            try {
                service.checkPassword(
                    acId = acId,
                    acPassword = acPassword
                )
                onSuccessLiveData.value = true
            } catch (e: HttpException) {
                e.printStackTrace()
            }
        }
    }

    fun changingPassword(
        acId: Int,
        acPassword: String
    ) {
        launch {
            try {
                service.changePassword(
                    acId = acId,
                    acPassword = acPassword
                )
                onSuccessLiveDataPassword.value = true
            } catch (e: HttpException) {
                e.printStackTrace()
            }
        }
    }
}