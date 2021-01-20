package com.istekno.coffeebreakapp.main.login

import android.os.Bundle
import android.view.WindowManager
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseActivity
import com.istekno.coffeebreakapp.databinding.ActivityLoginBinding
import com.istekno.coffeebreakapp.main.forgotpassword.ForgotPasswordActivity
import com.istekno.coffeebreakapp.main.maincontent.MainContentActivity

class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_login
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        )
        viewListener()
    }

    private fun viewListener() {
        binding?.tvForgotPassword?.setOnClickListener {
            intent<ForgotPasswordActivity>(this)
        }
        binding?.btnLogin?.setOnClickListener {
            intent<MainContentActivity>(this)
        }
    }
}