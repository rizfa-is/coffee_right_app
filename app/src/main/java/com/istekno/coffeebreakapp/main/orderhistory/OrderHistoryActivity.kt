package com.istekno.coffeebreakapp.main.orderhistory

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseActivityViewModel
import com.istekno.coffeebreakapp.databinding.ActivityOrderHistoryBinding
import com.istekno.coffeebreakapp.remote.ApiClient
import com.istekno.coffeebreakapp.utilities.SharedPreferenceUtil

class OrderHistoryActivity : BaseActivityViewModel<ActivityOrderHistoryBinding, OrderHistoryViewModel>(),
    OrderHistoryRecyclerViewAdapter.OnListOrderHistoryClickListener {

    private lateinit var sharedPref: SharedPreferenceUtil
    var listOrderHistory = ArrayList<OrderHistoryModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_order_history
        setViewModel = ViewModelProvider(this).get(OrderHistoryViewModel::class.java)
        super.onCreate(savedInstanceState)
        sharedPref = SharedPreferenceUtil(this)

        val service = ApiClient.getApiClient(this)?.create(OrderHistoryApiService::class.java)
        if (service != null) {
            viewModel.setService(service)
        }

        val customerId = sharedPref.getPreference().roleID

        setRecyclerView()
        viewModel.callOrderHistoryApi(customerId!!)
        subscribeLiveData()
        onClickListener()

    }

    private fun onClickListener() {
        binding.icBack.setOnClickListener {
            onBackPressed()
        }
    }

    fun subscribeLiveData() {
        viewModel.isLoading.observe(this) {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
                binding.rvOrderHistory.visibility = View.GONE
            } else {
                binding.progressBar.visibility = View.GONE
                binding.rvOrderHistory.visibility = View.VISIBLE
            }
        }

        viewModel.isNotFound.observe(this) {
            if (it) {
                binding.historyNotFound.visibility = View.VISIBLE
                binding.rvOrderHistory.visibility = View.GONE
                binding.progressBar.visibility = View.GONE
            } else {
                binding.historyNotFound.visibility = View.GONE
                binding.rvOrderHistory.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
            }
        }

        viewModel.listData.observe(this) {
            (binding.rvOrderHistory.adapter as OrderHistoryRecyclerViewAdapter).addList(it)
        }
    }

    private fun setRecyclerView() {
        binding.rvOrderHistory.isNestedScrollingEnabled = false
        binding.rvOrderHistory.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        val adapter = OrderHistoryRecyclerViewAdapter(listOrderHistory, this)
        binding.rvOrderHistory.adapter = adapter
    }

    override fun onOrderHistoryItemClicked(position: Int) {
        Toast.makeText(this, "item ${listOrderHistory[position].orderId} clicked", Toast.LENGTH_SHORT).show()
    }
}