package com.istekno.coffeebreakapp.main.checkout

import android.os.Bundle
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseActivity
import com.istekno.coffeebreakapp.databinding.ActivityCheckoutBinding
import com.istekno.coffeebreakapp.main.payment.PaymentActivity

class CheckoutActivity : BaseActivity<ActivityCheckoutBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_checkout
        super.onCreate(savedInstanceState)

        onClickListener()

    }

    private fun onClickListener() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnConfirmAndPay.setOnClickListener {
            intent<PaymentActivity>(this)
        }
    }
}