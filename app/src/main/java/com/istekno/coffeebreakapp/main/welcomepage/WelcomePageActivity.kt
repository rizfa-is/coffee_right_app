package com.istekno.coffeebreakapp.main.welcomepage

import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseActivity
import com.istekno.coffeebreakapp.databinding.ActivityWelcomePageBinding
import com.istekno.coffeebreakapp.main.mainpage.MainPageActivity

class WelcomePageActivity : BaseActivity<ActivityWelcomePageBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_welcome_page
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        window.setFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        )

        viewListener()
    }

    private fun viewListener() {
        binding?.btnGetStarted?.setOnClickListener {
            intent<MainPageActivity>(this)
        }
    }
}