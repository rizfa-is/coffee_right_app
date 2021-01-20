package com.istekno.coffeebreakapp.main.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseActivity
import com.istekno.coffeebreakapp.databinding.ActivitySignupBinding

class SignupActivity : BaseActivity<ActivitySignupBinding>() {

    private lateinit var viewModel: SignupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_signup
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(SignupViewModel::class.java)

        window.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        )
    }
}