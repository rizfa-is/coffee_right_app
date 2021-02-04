package com.istekno.coffeebreakapp.main.maincontent.orderhistory.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseActivity
import com.istekno.coffeebreakapp.base.BaseActivityViewModel
import com.istekno.coffeebreakapp.databinding.ActivityDetailOrderHistoryBinding
import com.istekno.coffeebreakapp.main.maincontent.orderhistory.OrderHistoryApiService
import com.istekno.coffeebreakapp.main.maincontent.orderhistory.OrderHistoryResponse
import com.istekno.coffeebreakapp.remote.ApiClient

class DetailOrderHistoryActivity :
    BaseActivity<ActivityDetailOrderHistoryBinding>() {

    companion object {
        const val ORDER_HISTORY_KEY = "orID_KEY"
    }

    var listOrder = ArrayList<OrderHistoryResponse.Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_detail_order_history
        super.onCreate(savedInstanceState)

        val data = intent.getParcelableExtra<OrderHistoryResponse.Data>(ORDER_HISTORY_KEY)
        binding.model = data

        setRecyclerView()
        onClickListener()
        (binding.rvListOrder.adapter as DetailOrderHistoryRecyclerViewAdapter).addList(data!!.productOrder)

    }

    fun onClickListener() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setRecyclerView() {
        binding.rvListOrder.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        val adapter = DetailOrderHistoryRecyclerViewAdapter(listOrder)
        binding.rvListOrder.adapter = adapter


    }
}