package com.istekno.coffeebreakapp.main.orderhistory

import android.os.Bundle
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseActivityViewModel
import com.istekno.coffeebreakapp.databinding.ActivityOrderHistoryBinding

class OrderHistoryActivity : BaseActivityViewModel<ActivityOrderHistoryBinding, OrderHistoryViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_order_history
        setViewModel = ViewModelProvider(this).get(OrderHistoryViewModel::class.java)
        super.onCreate(savedInstanceState)

        window.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        )
    }
}