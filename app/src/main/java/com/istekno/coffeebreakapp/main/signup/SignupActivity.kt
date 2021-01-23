package com.istekno.coffeebreakapp.main.signup

import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseActivityViewModel
import com.istekno.coffeebreakapp.databinding.ActivitySignupBinding
import com.istekno.coffeebreakapp.main.login.LoginActivity
import com.istekno.coffeebreakapp.remote.ApiClient
import retrofit2.create

class SignupActivity : BaseActivityViewModel<ActivitySignupBinding, SignupViewModel>() {

    companion object {
        const val FIELD_REQUIRED = "Field must not empty"
        const val FIELD_IS_NOT_VALID = "Email format is not valid"
        const val FIELD_LENGTH = "Password min. 8 characters"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_signup
        setViewModel = ViewModelProvider(this).get(SignupViewModel::class.java)
        super.onCreate(savedInstanceState)
        window.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        )
        val service = ApiClient.getApiClient(this)?.create(SignUpApiService::class.java)
        if (service != null) {
            viewModel.setService(service)
        }


        viewListener()
    }

    private fun viewListener() {
        binding.btnCreateAccount.setOnClickListener {
            validation()
        }
    }

    private fun validation() {
        val inputEmail = binding.etEmail.text.toString()
        val inputPassword = binding.etPassword.text.toString()
        val inputPhoneNumber = binding.etPhoneNumber.text.toString()

        if (inputEmail.isEmpty()) {
            showToast(FIELD_REQUIRED)
            return
        }

        if (!inputEmail.contains('@') || !inputEmail.contains('.')) {
            showToast(FIELD_IS_NOT_VALID)
            return
        }

        if (inputPassword.isEmpty()) {
            showToast(FIELD_REQUIRED)
            return
        }

        if (inputPassword.length < 8) {
            showToast(FIELD_LENGTH)
            return
        }

        if (inputPhoneNumber.isEmpty()) {
            showToast(FIELD_REQUIRED)
            return
        }
        viewModel.callSignUpService("", inputEmail, inputPassword, inputPhoneNumber)
        subscribeLiveData()

    }

    private fun subscribeLiveData() {
        viewModel.isRegister.observe(this, Observer {
            if (it) {
                viewModel.isMessage.observe(this, Observer { it1->
                    showToast(it1)
                })
                intent<LoginActivity>(this)
            } else {
                viewModel.isMessage.observe(this, Observer { it1->
                    showToast(it1)
                })
            }
        })
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}