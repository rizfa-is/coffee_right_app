package com.istekno.coffeebreakapp.main.mainpage

import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseActivity
import com.istekno.coffeebreakapp.databinding.ActivityMainPageBinding

class MainPageActivity : BaseActivity<ActivityMainPageBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_main_page
        super.onCreate(savedInstanceState)

        window.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        )

        viewListener()
    }

    private fun viewListener() {
        binding?.btnCreateAccount?.setOnClickListener {
            Toast.makeText(this, "Selected Create New Account", Toast.LENGTH_LONG).show()
        }
        binding?.btnLogin?.setOnClickListener {
            Toast.makeText(this, "Selected Login", Toast.LENGTH_LONG).show()
        }
    }
}