package com.istekno.coffeebreakapp.main.maincontent.mainactivity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.istekno.coffeebreakapp.main.maincontent.homepage.HomeResponse
import com.istekno.coffeebreakapp.model.SharedPrefModel
import com.istekno.coffeebreakapp.utilities.SharedPreferenceUtil
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainContentViewModel: ViewModel(), CoroutineScope {

    var listProduct = MutableLiveData<List<HomeResponse.DataProduct>>()
    val productAction = MutableLiveData<Boolean>()
    val isFailedStatus = MutableLiveData<Boolean>()

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

    fun getAllProductByQuery(type: Int, query: String, filter: Int) {
        launch {

            productAction.value = true
            isFailedStatus.value = false

            val result = withContext(Job() + Dispatchers.IO) {
                try {

                    if (type == 0) {
                        service.getProductByQuery(" ")
                    } else {
                        service.getProductByQuery(query)
                    }

                } catch (e: Throwable) {
                    e.printStackTrace()

                    withContext(Job() + Dispatchers.Main) {
                        productAction.value = false
                        isFailedStatus.value = true
                    }
                }
            }

            if (result is HomeResponse) {
                var mutableList = mutableListOf<HomeResponse.DataProduct>()
                val list = result.data.map {
                    HomeResponse.DataProduct(it.productId, it.discountId, it.productName, it.productDesc, it.productPrice, it.productImage, it.productFavorite, it.productCategory, it.productCreated, it.productUpdated)
                }
                mutableList = list.toMutableList()

                when(filter) {
                    1 -> { mutableList.removeIf { it.productCategory != "Food" } }
                    2 -> { mutableList.removeIf { it.productCategory != "Drink" } }
                }

                if (mutableList.isNullOrEmpty()) {
                    isFailedStatus.value = true
                }

                listProduct.value = mutableList
                productAction.value = false
            }
        }
    }
}