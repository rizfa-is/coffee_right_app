package com.istekno.coffeebreakapp.main.forgotpassword

import android.os.Bundle
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseActivity
import com.istekno.coffeebreakapp.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : BaseActivity<ActivityForgotPasswordBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_forgot_password
        super.onCreate(savedInstanceState)
    }
}