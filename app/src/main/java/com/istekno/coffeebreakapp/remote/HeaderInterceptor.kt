package com.istekno.coffeebreakapp.remote

import android.content.Context
import android.util.Log
import com.istekno.coffeebreakapp.utilities.SharedPreferenceUtil
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(context: Context): Interceptor {

    private val tokenAuth = SharedPreferenceUtil(context).getPreference().token

    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request().newBuilder()
                .addHeader("Authorization","Bearer $tokenAuth")
                .build()
        )
    }
}