package com.istekno.coffeebreakapp.main.orderhistory.detail

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseActivityViewModel
import com.istekno.coffeebreakapp.databinding.ActivityDetailOrderHistoryBinding
import com.istekno.coffeebreakapp.main.orderhistory.OrderHistoryApiService
import com.istekno.coffeebreakapp.main.orderhistory.OrderHistoryRecyclerViewAdapter
import com.istekno.coffeebreakapp.remote.ApiClient

class DetailOrderHistoryActivity : BaseActivityViewModel<ActivityDetailOrderHistoryBinding, DetailOrderHistoryViewModel>() {

    companion object {
        const val ORDER_HISTORY_KEY = "orID_KEY"
        const val PRICE_BEFORE_TAX = "PRICE_BEFORE_TAX"
        const val TAX = "TAX"
        const val TOTAL_PRICE = "TOTAL_PRICE"
    }

    var listOrder = ArrayList<DetailOrderHistoryModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_detail_order_history
        setViewModel = ViewModelProvider(this).get(DetailOrderHistoryViewModel::class.java)
        super.onCreate(savedInstanceState)

        val service = ApiClient.getApiClient(this)?.create(OrderHistoryApiService::class.java)
        if (service != null) {
            viewModel.setService(service)
        }

        val id = intent.getStringExtra(ORDER_HISTORY_KEY)

        setRecyclerView()
        viewModel.callOrderApiService(id!!.toInt())
        subscribeLiveData()
        onClickListener()

    }

    fun onClickListener() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    fun subscribeLiveData() {
        viewModel.isLoading.observe(this) {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
                binding.scrollView.visibility = View.GONE
            } else {
                binding.progressBar.visibility = View.GONE
                binding.scrollView.visibility = View.VISIBLE
            }
        }

        viewModel.listData.observe(this) {
            (binding.rvListOrder.adapter as DetailOrderHistoryRecyclerViewAdapter).addList(it)
            binding.tvTax.text = intent.getStringExtra(TAX)
            binding.tvSubtotal.text = intent.getStringExtra(PRICE_BEFORE_TAX)
            binding.tvTotal.text = intent.getStringExtra(TOTAL_PRICE)
        }
    }

    private fun setRecyclerView() {
        binding.rvListOrder.isNestedScrollingEnabled = false
        binding.rvListOrder.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        val adapter = DetailOrderHistoryRecyclerViewAdapter(listOrder)
        binding.rvListOrder.adapter = adapter
    }
}