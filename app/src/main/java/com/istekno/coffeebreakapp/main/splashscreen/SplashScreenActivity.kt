package com.istekno.coffeebreakapp.main.splashscreen

import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseActivityViewModel
import com.istekno.coffeebreakapp.databinding.ActivitySplashScreenBinding
import com.istekno.coffeebreakapp.main.maincontent.mainactivity.MainContentActivity
import com.istekno.coffeebreakapp.main.welcomepage.WelcomePageActivity
import com.istekno.coffeebreakapp.remote.ApiClient
import com.istekno.coffeebreakapp.utilities.SharedPreferenceUtil

class SplashScreenActivity : BaseActivityViewModel<ActivitySplashScreenBinding, SplashScreenViewModel>() {

    private lateinit var sharePref: SharedPreferenceUtil

    companion object {
        private const val splashDuration = 2000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_splash_screen
        setViewModel = ViewModelProvider(this).get(SplashScreenViewModel::class.java)
        super.onCreate(savedInstanceState)

        val service = ApiClient.getApiClient(this)?.create(CheckJwtExpiredApiService::class.java)
        if (service != null) {
            viewModel.setService(service)
        }

        sharePref = SharedPreferenceUtil(this)

        viewModel.setSharePref(sharePref)
        viewModel.checkLoginStatus()
        viewModel.callApiService()


        window.setFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        )

        Handler(mainLooper).postDelayed(
            {
                subscribeLiveData()
                finish()
            }, splashDuration.toLong()
        )
    }

    private fun subscribeLiveData() {
        viewModel.isRemember.observe(this, Observer {
            if (it) {
                viewModel.checkJwt.observe(this) {it1 ->
                    if (it1) {
                        intent<MainContentActivity>(this)
                        finish()
                    } else {
                        intent<WelcomePageActivity>(this)
                        finish()
                    }
                }
//                intent<MainContentActivity>(this)
//                finish()
            } else {
                intent<WelcomePageActivity>(this)
                finish()
            }
        })
    }

    private fun checkJwtExpired() {

    }
}