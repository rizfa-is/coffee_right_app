package com.istekno.coffeebreakapp.main.login

import android.os.Bundle
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseActivityViewModel
import com.istekno.coffeebreakapp.databinding.ActivityLoginBinding
import com.istekno.coffeebreakapp.main.forgotpassword.ForgotPasswordActivity
import com.istekno.coffeebreakapp.main.maincontent.MainContentActivity

class LoginActivity : BaseActivityViewModel<ActivityLoginBinding, LoginViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_login
        setViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        super.onCreate(savedInstanceState)

        viewListener()
    }

    private fun viewListener() {
        binding.tvForgotPassword.setOnClickListener {
            intent<ForgotPasswordActivity>(this)
        }
        binding.btnLogin.setOnClickListener {
            intent<MainContentActivity>(this)
        }
    }
}