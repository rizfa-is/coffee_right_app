package com.istekno.coffeebreakapp.main.maincontent.maincontent

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.istekno.coffeebreakapp.main.login.LoginApiService
import com.istekno.coffeebreakapp.main.login.LoginResponse
import com.istekno.coffeebreakapp.model.SharedPrefModel
import com.istekno.coffeebreakapp.utilities.SharedPreferenceUtil
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainContentViewModel: ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    private lateinit var service: MainContentService
    private lateinit var sharePref: SharedPreferenceUtil

    fun setService(service: MainContentService) {
        this.service = service
    }

    fun setSharePref(sharePref: SharedPreferenceUtil) {
        this.sharePref = sharePref
    }

    fun getCustomerData() {
        launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    service.getCustomerByID(sharePref.getPreference().roleID!!)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (result is MainContentResponse) {
                val data = sharePref.getPreference().level
                val res = result.data?.get(0)

                if (data == 0) {
                    sharePref.setPreference(SharedPrefModel(res?.accountId, res?.accountEmail, res?.accountName, res?.accountImage,
                        res?.accountAddress, res?.customerId, data, sharePref.getPreference().token, true))
                }
            }
        }
    }




}