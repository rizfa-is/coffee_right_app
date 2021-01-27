package com.istekno.coffeebreakapp.main.editprofile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

class EditProfileViewModel: ViewModel(), CoroutineScope {
    private lateinit var serviceAccount: AccountApi
    private lateinit var serviceCustomer: CustomerApi

    val onSuccessLiveData = MutableLiveData<Boolean>()
    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setServiceAccount(serviceAccount: AccountApi) {
        this@EditProfileViewModel.serviceAccount = serviceAccount
    }
    fun setServiceCustomer(serviceCustomer: CustomerApi) {
        this@EditProfileViewModel.serviceCustomer = serviceCustomer
    }



    fun updateAPI(
        csId: Int,
        acId: Int,
        acName: String,
        acPhone: String,
        csGender: RequestBody,
        csBirthday: RequestBody,
        csAddress: RequestBody,
        image: MultipartBody.Part? = null

    ) {
        launch {
            try {
                serviceAccount.updateAccount(
                    acId = acId,
                    acName = acName,
                    acPhone = acPhone
                )
                serviceCustomer.updateCustomer(
                    csId = csId,
                    csGender = csGender,
                    csBirthday = csBirthday,
                    csAddress = csAddress,
                    image = image
                )
                onSuccessLiveData.value = true
            } catch (e: HttpException) {
                e.printStackTrace()
            }
        }
    }
}