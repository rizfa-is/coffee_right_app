package com.istekno.coffeebreakapp.main.orderhistory.detail

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseActivityViewModel
import com.istekno.coffeebreakapp.databinding.ActivityDetailOrderHistoryBinding

class DetailOrderHistoryActivity : BaseActivityViewModel<ActivityDetailOrderHistoryBinding, DetailOrderHistoryViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_detail_order_history
        setViewModel = ViewModelProvider(this).get(DetailOrderHistoryViewModel::class.java)
        super.onCreate(savedInstanceState)

    }
}