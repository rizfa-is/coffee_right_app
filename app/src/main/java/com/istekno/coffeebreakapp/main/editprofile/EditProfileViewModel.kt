package com.istekno.coffeebreakapp.main.editprofile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

class EditProfileViewModel: ViewModel(), CoroutineScope {
    private lateinit var service: EditProfileApi

    val onSuccessLiveData = MutableLiveData<Boolean>()
    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setService(serviceEditProfile: EditProfileApi) {
        this@EditProfileViewModel.service = serviceEditProfile
    }

    fun updateAPIAccount(
        acId: Int,
        acName: String,
        acPhone: String
    ) {
        launch {
            try {
                service.updateAccount(
                    acId = acId,
                    acName = acName,
                    acPhone = acPhone
                )
                onSuccessLiveData.value = true
            } catch (e: HttpException) {
                e.printStackTrace()
            }
        }
    }

    fun updateAPICustomer(
        csId: Int,
        csGender: RequestBody,
        csBirthday: RequestBody,
        csAddress: RequestBody? = null,
        image: MultipartBody.Part? = null

    ) {
        launch {
            try {
                service.updateCustomer(
                    csId = csId,
                    csGender = csGender,
                    csBirthday = csBirthday,
                    csAddress = csAddress!!,
                    image = image
                )
                onSuccessLiveData.value = true
            } catch (e: HttpException) {
                e.printStackTrace()
            }
        }
    }
}