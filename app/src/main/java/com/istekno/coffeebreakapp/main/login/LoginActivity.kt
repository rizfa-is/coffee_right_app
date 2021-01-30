package com.istekno.coffeebreakapp.main.login

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseActivityViewModel
import com.istekno.coffeebreakapp.databinding.ActivityLoginBinding
import com.istekno.coffeebreakapp.main.maincontent.mainactivity.MainContentActivity
import com.istekno.coffeebreakapp.main.forgotpassword.checkemail.CheckEmailActivity
import com.istekno.coffeebreakapp.main.signup.SignupActivity
import com.istekno.coffeebreakapp.remote.ApiClient
import com.istekno.coffeebreakapp.utilities.SharedPreferenceUtil

class LoginActivity : BaseActivityViewModel<ActivityLoginBinding, LoginViewModel>() {

    private lateinit var sharePref: SharedPreferenceUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_login
        setViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        sharePref = SharedPreferenceUtil(this)
        super.onCreate(savedInstanceState)

        val service = ApiClient.getApiClient(this)?.create(LoginApiService::class.java)

        viewModel.setSharePref(sharePref)
        if (service != null) {
            viewModel.setService(service)
        }
        viewListener()
    }

    private fun viewListener() {
        binding.tvForgotPassword.setOnClickListener {
            intent<CheckEmailActivity>(this)
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

        viewModel.callLoginApi(inputEmail, inputPassword)
        subscribeLiveData()
    }

    private fun subscribeLiveData() {
        viewModel.isDataLogin.observe(this, Observer {
            if (it) {
                showToast("Login Success")
                intent<MainContentActivity>(this)
                finish()
            } else {
                showToast("Email/Password Wrong")
            }
        })
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}