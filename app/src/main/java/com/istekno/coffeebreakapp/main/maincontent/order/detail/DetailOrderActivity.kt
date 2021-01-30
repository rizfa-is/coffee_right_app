package com.istekno.coffeebreakapp.main.maincontent.order.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseActivityViewModel
import com.istekno.coffeebreakapp.databinding.ActivityDetailOrderBinding
import com.istekno.coffeebreakapp.main.orderhistory.OrderHistoryApiService
import com.istekno.coffeebreakapp.main.orderhistory.detail.DetailOrderHistoryModel
import com.istekno.coffeebreakapp.main.orderhistory.detail.DetailOrderHistoryRecyclerViewAdapter
import com.istekno.coffeebreakapp.remote.ApiClient

class DetailOrderActivity : BaseActivityViewModel<ActivityDetailOrderBinding, DetailOrderViewModel>() {

    companion object {
        const val ORDER_HISTORY_KEY = "orID_KEY"
        const val PRICE_BEFORE_TAX = "PRICE_BEFORE_TAX"
        const val TAX = "TAX"
        const val TOTAL_PRICE = "TOTAL_PRICE"
    }

    private var listOrder = ArrayList<DetailOrderHistoryModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_detail_order
        setViewModel = ViewModelProvider(this).get(DetailOrderViewModel::class.java)
        super.onCreate(savedInstanceState)

        val service = ApiClient.getApiClient(this)?.create(OrderHistoryApiService::class.java)
        if (service != null) {
            viewModel.setService(service)
        }

        val id = intent.getIntExtra(ORDER_HISTORY_KEY, -1)

        setRecyclerView()
        viewModel.callOrderApiService(id)
        subscribeLoadingLiveData()
        subscribeGetListLiveData()
        onClickListener()
    }

    private fun subscribeLoadingLiveData() {
        viewModel.isLoading.observe(this, Observer {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
                binding.scrollView.visibility = View.GONE
            } else {
                binding.progressBar.visibility = View.GONE
                binding.scrollView.visibility = View.VISIBLE
            }
        })
    }

    private fun setRecyclerView() {
        binding.rvListOrder.isNestedScrollingEnabled = false
        binding.rvListOrder.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        val adapter = DetailOrderHistoryRecyclerViewAdapter(listOrder)
        binding.rvListOrder.adapter = adapter
    }

    private fun subscribeGetListLiveData() {
        viewModel.isGetList.observe(this, {
            if (it) {
                viewModel.listData.observe(this, { list->
                    (binding.rvListOrder.adapter as DetailOrderHistoryRecyclerViewAdapter).addList(list)
                    binding.tvTax.text = intent.getIntExtra(TAX,-1).toString()
                    Log.d("tax:", intent.getIntExtra(TAX,-1).toString())
                    binding.tvSubtotal.text = intent.getIntExtra(PRICE_BEFORE_TAX, -1).toString()
                    binding.tvTotal.text = intent.getIntExtra(TOTAL_PRICE, -1).toString()
                })
            } else {
                Toast.makeText(this, "Something wrong ...", Toast.LENGTH_SHORT).show()
            }
        })
//        binding.tvTax.text = intent.getIntExtra(TAX,-1).toString()
//        Log.d("tax:", intent.getIntExtra(TAX,-1).toString())
//        binding.tvSubtotal.text = intent.getIntExtra(PRICE_BEFORE_TAX, -1).toString()
//        binding.tvTotal.text = intent.getIntExtra(TOTAL_PRICE, -1).toString()

    }

    private fun onClickListener() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }
}