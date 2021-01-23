package com.istekno.coffeebreakapp.main.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseActivityViewModel
import com.istekno.coffeebreakapp.databinding.ActivitySplashScreenBinding
import com.istekno.coffeebreakapp.main.mainpage.MainPageActivity

class SplashScreenActivity : BaseActivityViewModel<ActivitySplashScreenBinding, SplashScreenViewModel>() {

    companion object {
        private const val splashDuration = 2000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_splash_screen
        setViewModel = ViewModelProvider(this).get(SplashScreenViewModel::class.java)
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        )

        Handler(mainLooper).postDelayed(
            {
                startActivity(Intent(this, MainPageActivity::class.java))
                finish()
            }, splashDuration.toLong()
        )
    }
}