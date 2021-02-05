package com.istekno.coffeebreakapp.main.payment

import android.content.Intent
import android.os.Bundle
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseActivity
import com.istekno.coffeebreakapp.databinding.ActivitySuccessOrderScreenBinding
import com.istekno.coffeebreakapp.main.maincontent.mainactivity.MainContentActivity

class SuccessOrderScreenActivity : BaseActivity<ActivitySuccessOrderScreenBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_success_order_screen
        super.onCreate(savedInstanceState)

        onClickListener()
    }

    private fun onClickListener() {
        binding.btnPayNow.setOnClickListener {
            val intent = Intent(this, MainContentActivity::class.java)
            intent.putExtra("data", 0)
            startActivity(intent)
            finishAffinity()
        }
    }
}