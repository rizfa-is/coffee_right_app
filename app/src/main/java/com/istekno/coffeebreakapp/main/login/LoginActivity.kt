package com.istekno.coffeebreakapp.main.login

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseActivityViewModel
import com.istekno.coffeebreakapp.databinding.ActivityLoginBinding
import com.istekno.coffeebreakapp.main.forgotpassword.ForgotPasswordActivity
import com.istekno.coffeebreakapp.main.maincontent.MainContentActivity
import com.istekno.coffeebreakapp.main.signup.SignupActivity

class LoginActivity : BaseActivityViewModel<ActivityLoginBinding, LoginViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_login
        setViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        )
        viewListener()
    }

    private fun viewListener() {
        binding.tvForgotPassword.setOnClickListener {
            intent<ForgotPasswordActivity>(this)
        }
        binding.btnLogin.setOnClickListener {
            validation()
        }
    }

    private fun validation() {
        val inputEmail = binding.etEmail.text.toString()
        val inputPassword = binding.etPassword.text.toString()

        if (inputEmail.isEmpty()) {
            showToast(SignupActivity.FIELD_REQUIRED)
            return
        }

        if (!inputEmail.contains('@') || !inputEmail.contains('.')) {
            showToast(SignupActivity.FIELD_IS_NOT_VALID)
            return
        }

        if (inputPassword.isEmpty()) {
            showToast(SignupActivity.FIELD_REQUIRED)
            return
        }

        if (inputPassword.length < 8) {
            showToast(SignupActivity.FIELD_LENGTH)
            return
        }

        showToast("Login Success")
        intent<MainContentActivity>(this)
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }


}