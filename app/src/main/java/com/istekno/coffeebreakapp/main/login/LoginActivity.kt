package com.istekno.coffeebreakapp.main.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseActivity
import com.istekno.coffeebreakapp.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_login
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        )

    }
}