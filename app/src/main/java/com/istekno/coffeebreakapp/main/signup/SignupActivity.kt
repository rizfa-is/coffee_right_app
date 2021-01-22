package com.istekno.coffeebreakapp.main.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseActivity
import com.istekno.coffeebreakapp.base.BaseActivityViewModel
import com.istekno.coffeebreakapp.databinding.ActivitySignupBinding

class SignupActivity : BaseActivityViewModel<ActivitySignupBinding, SignupViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_signup
        setViewModel = ViewModelProvider(this).get(SignupViewModel::class.java)
        super.onCreate(savedInstanceState)
    }
}