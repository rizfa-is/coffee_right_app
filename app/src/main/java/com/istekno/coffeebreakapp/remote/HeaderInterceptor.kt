package com.istekno.coffeebreakapp.remote

import android.content.Context
import android.util.Log
import com.istekno.coffeebreakapp.utilities.SharedPreferenceUtil
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(private val context: Context) : Interceptor {

    private lateinit var sharedPref: SharedPreferenceUtil

    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        sharedPref = SharedPreferenceUtil(context = context)

        val tokenAuth = sharedPref.getPreference().token
        Log.d("tokenAuth:", tokenAuth.toString())

        proceed(
            request().newBuilder()
                .addHeader("Authorization", "Bearer $tokenAuth")
                .build()
        )
    }
}