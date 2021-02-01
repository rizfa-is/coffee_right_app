package com.istekno.coffeebreakapp.main.maincontent.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.istekno.coffeebreakapp.main.maincontent.homepage.HomeService
import com.istekno.coffeebreakapp.utilities.SharedPreferenceUtil
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ProfileViewModel : ViewModel(), CoroutineScope {

    val isLoading = MutableLiveData<Boolean>()
    val listData = MutableLiveData<List<ProfileModel>>()
    val isGetData = MutableLiveData<Boolean>()
    val isMessage = MutableLiveData<String>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    private lateinit var service: ProfileService
    private lateinit var sharedPref: SharedPreferenceUtil

    fun setService(service: ProfileService) {
        this.service = service
    }

    fun setSharedPref(sharedPref: SharedPreferenceUtil) {
        this.sharedPref = sharedPref
    }

    fun getCustomerByID() {
        launch {

            isLoading.value = true
            val result = withContext(Job() + Dispatchers.IO) {
                try {
                    service.getCustomerByID(sharedPref.getPreference().roleID!!)
                } catch (e: Throwable) {
                    e.printStackTrace()

                    withContext(Job() + Dispatchers.Main) {
                        isLoading.value = false
                        isGetData.value = false
                    }
                }
            }

            if (result is ProfileResponse) {
                isGetData.value = result.success
                val list = result.data?.map {
                    ProfileModel(
                        it.customerId,
                        it.accountId,
                        it.accountName,
                        it.accountEmail,
                        it.accountPhone,
                        it.accountGender,
                        it.accountBirthday,
                        it.accountAddress,
                        it.accountImage
                    )
                }

                listData.value = list
                isMessage.value = result.message
                isLoading.value = false
            } else {
                isGetData.value = false
                isLoading.value = false
                isMessage.value = "Something wrong..."
            }
        }
    }
}