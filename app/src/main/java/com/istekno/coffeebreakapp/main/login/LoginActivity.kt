package com.istekno.coffeebreakapp.main.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseActivity
import com.istekno.coffeebreakapp.databinding.ActivityLoginBinding
<<<<<<< HEAD
import com.istekno.coffeebreakapp.main.maincontent.MainContentActivity
import com.istekno.coffeebreakapp.main.mainpage.MainPageActivity
=======
import com.istekno.coffeebreakapp.main.forgotpassword.ForgotPasswordActivity
import com.istekno.coffeebreakapp.main.orderhistory.OrderHistoryActivity
>>>>>>> 426ea71b86ba37f1869d4d180c6a064f85fc3986

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

<<<<<<< HEAD
        viewListener()
    }

    private fun viewListener() {
        binding?.btnLogin?.setOnClickListener {
            intent<MainContentActivity>(this)
=======
        binding?.btnLogin?.setOnClickListener {
            intent<OrderHistoryActivity>(this)
>>>>>>> 426ea71b86ba37f1869d4d180c6a064f85fc3986
        }
    }
}