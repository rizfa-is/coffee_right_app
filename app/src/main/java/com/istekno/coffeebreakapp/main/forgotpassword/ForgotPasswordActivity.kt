package com.istekno.coffeebreakapp.main.forgotpassword

import android.os.Bundle
import android.widget.Toast
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseActivity
import com.istekno.coffeebreakapp.databinding.ActivityForgotPasswordBinding
import com.istekno.coffeebreakapp.main.signup.SignupActivity

class ForgotPasswordActivity : BaseActivity<ActivityForgotPasswordBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_forgot_password
        super.onCreate(savedInstanceState)

        viewListener()
    }

    private fun viewListener() {
        binding.btnResendLink.setOnClickListener {
            validation()
        }
    }

    private fun validation() {
        val inputEmail = binding.etEmail.text.toString()

        if (inputEmail.isEmpty()) {
            showToast(SignupActivity.FIELD_REQUIRED)
            return
        }

        if (!inputEmail.contains('@') || !inputEmail.contains('.')) {
            showToast(SignupActivity.FIELD_IS_NOT_VALID)
            return
        }
        showToast("Success")
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}