package com.istekno.coffeebreakapp.main.orderhistory

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseActivityViewModel
import com.istekno.coffeebreakapp.databinding.ActivityOrderHistoryBinding
import com.istekno.coffeebreakapp.main.maincontent.MainContentActivity
import com.istekno.coffeebreakapp.main.orderhistory.detail.DetailOrderHistoryActivity
import com.istekno.coffeebreakapp.remote.ApiClient
import com.istekno.coffeebreakapp.utilities.SharedPreferenceUtil

class OrderHistoryActivity : BaseActivityViewModel<ActivityOrderHistoryBinding, OrderHistoryViewModel>(),
    OrderHistoryRecyclerViewAdapter.OnListOrderHistoryClickListener {

    companion object {
        const val ORDER_HISTORY_KEY = "orID_KEY"
        const val PRICE_BEFORE_TAX = "PRICE_BEFORE_TAX"
        const val TAX = "TAX"
        const val TOTAL_PRICE = "TOTAL_PRICE"
    }

    private lateinit var sharedPref: SharedPreferenceUtil
    private var listOrderHistory = ArrayList<OrderHistoryModel>()

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

        binding.btnStartOrder.setOnClickListener {
            intent<MainContentActivity>(this)
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

        viewModel.isGetList.observe(this, Observer {
            if (it) {
                viewModel.listData.observe(this, Observer { list->
                    (binding.rvOrderHistory.adapter as OrderHistoryRecyclerViewAdapter).addList(list)
                })
                binding.historyNotFound.visibility = View.GONE
                binding.rvOrderHistory.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
            } else {
                binding.historyNotFound.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
            }
        })

    }

    private fun setRecyclerView() {
        binding.rvOrderHistory.isNestedScrollingEnabled = false
        binding.rvOrderHistory.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        val adapter = OrderHistoryRecyclerViewAdapter(listOrderHistory, this)
        binding.rvOrderHistory.adapter = adapter
    }

    override fun onOrderHistoryItemClicked(position: Int) {
        Toast.makeText(this, "item ${listOrderHistory[position].orderId} clicked", Toast.LENGTH_SHORT).show()
        val sendIntent = Intent(this, DetailOrderHistoryActivity::class.java)
        sendIntent.putExtra(ORDER_HISTORY_KEY, listOrderHistory[position].orderId)
        sendIntent.putExtra(PRICE_BEFORE_TAX, listOrderHistory[position].priceBeforeTax)
        sendIntent.putExtra(TAX, listOrderHistory[position].orderTax)
        sendIntent.putExtra(TOTAL_PRICE, listOrderHistory[position].totalPrice)
        startActivity(sendIntent)
    }
}