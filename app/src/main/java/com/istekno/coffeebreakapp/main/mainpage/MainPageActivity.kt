package com.istekno.coffeebreakapp.main.mainpage

import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseActivity
import com.istekno.coffeebreakapp.databinding.ActivityMainPageBinding
import com.istekno.coffeebreakapp.main.login.LoginActivity
import com.istekno.coffeebreakapp.main.signup.SignupActivity

class MainPageActivity : BaseActivity<ActivityMainPageBinding>() {

    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_main_page
        super.onCreate(savedInstanceState)

        viewListener()
    }

    private fun viewListener() {
        binding.btnCreateAccount.setOnClickListener {
            intent<SignupActivity>(this)
        }
        binding.btnLogin.setOnClickListener {
            intent<LoginActivity>(this)
        }
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        doubleBackToExitPressedOnce = true
        showToast("Please click BACK again to exit")
        Handler(mainLooper).postDelayed( { doubleBackToExitPressedOnce = false }, 2000)
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}