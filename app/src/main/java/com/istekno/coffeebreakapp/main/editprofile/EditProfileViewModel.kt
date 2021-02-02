package com.istekno.coffeebreakapp.main.editprofile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

class EditProfileViewModel: ViewModel(), CoroutineScope {

    val onSuccessLiveData = MutableLiveData<Boolean>()
    val onMessageLiveData = MutableLiveData<String>()
    val onFailLiveData = MutableLiveData<String>()
    val isLoading = MutableLiveData<Boolean>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    private lateinit var service: EditProfileApi

    fun setService(serviceEditProfile: EditProfileApi) {
        this@EditProfileViewModel.service = serviceEditProfile
    }

    fun updateApi(
        acId: Int,
        acName: String,
        acEmail: String,
        acPhone: String,
        csId: Int,
        csGender: RequestBody,
        csBirthday: RequestBody,
        csAddress: RequestBody? = null,
        image: MultipartBody.Part? = null
    ) {
        launch {
            isLoading.value = true
            val response = withContext(Dispatchers.IO) {
                try {
                    service.updateAccount(
                        acId = acId,
                        acName = acName,
                        acEmail = acEmail,
                        acPhone = acPhone
                    )
                    service.updateCustomer(
                        csId = csId,
                        csGender = csGender,
                        csBirthday = csBirthday,
                        csAddress = csAddress!!,
                        image = image
                    )
                } catch (e: HttpException) {
                    withContext(Dispatchers.Main) {
                        onSuccessLiveData.value = false

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
            if (response is EditProfileResponse) {
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
}