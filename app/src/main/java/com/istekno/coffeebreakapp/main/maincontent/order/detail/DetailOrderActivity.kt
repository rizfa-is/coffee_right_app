package com.istekno.coffeebreakapp.main.maincontent.order.detail

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
import com.istekno.coffeebreakapp.databinding.ActivityDetailOrderBinding
import com.istekno.coffeebreakapp.main.maincontent.mainactivity.MainContentActivity
import com.istekno.coffeebreakapp.main.maincontent.order.OrderApiService
import com.istekno.coffeebreakapp.main.maincontent.order.OrderResponse
import com.istekno.coffeebreakapp.main.maincontent.orderhistory.OrderHistoryResponse
import com.istekno.coffeebreakapp.main.maincontent.orderhistory.detail.DetailOrderHistoryModel
import com.istekno.coffeebreakapp.main.maincontent.orderhistory.detail.DetailOrderHistoryRecyclerViewAdapter
import com.istekno.coffeebreakapp.remote.ApiClient
import com.istekno.coffeebreakapp.utilities.SharedPreferenceUtil

class DetailOrderActivity :
    BaseActivityViewModel<ActivityDetailOrderBinding, DetailOrderViewModel>() {

    companion object {
        const val ORDER_HISTORY_KEY = "orID_KEY"
        const val PRICE_BEFORE_TAX = "PRICE_BEFORE_TAX"
        const val TAX = "TAX"
        const val TOTAL_PRICE = "TOTAL_PRICE"
    }

    private var listOrder = ArrayList<OrderResponse.Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_detail_order
        setViewModel = ViewModelProvider(this).get(DetailOrderViewModel::class.java)
        super.onCreate(savedInstanceState)

        val sharedPref = SharedPreferenceUtil(this)
        val service = ApiClient.getApiClient(this)?.create(OrderApiService::class.java)
        if (service != null) {
            viewModel.setService(service)
        }

        val data = intent.getParcelableExtra<OrderResponse.Data>(ORDER_HISTORY_KEY)
        binding.model = data

        if (sharedPref.getPreference().level == 0) {
            binding.btnUpdateDone.visibility = View.GONE
        } else {
            binding.btnUpdateDone.visibility = View.VISIBLE
        }

        setRecyclerView()
        subscribeUpdateLiveData()
        if (data != null) {
            onClickListener(data.orderDetailId)
            (binding.rvListOrder.adapter as DetailOrderRecyclerViewAdapter).addList(data.productOrder)
        }

    }

    private fun subscribeUpdateLiveData() {
        viewModel.isUpdate.observe(this, Observer {
            if (it) {
                Toast.makeText(this, "Updated successfully!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainContentActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Failed to update!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setRecyclerView() {
        binding.rvListOrder.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        val adapter = DetailOrderRecyclerViewAdapter(listOrder)
        binding.rvListOrder.adapter = adapter
    }

    private fun onClickListener(odId: Int) {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnUpdateDone.setOnClickListener {
            viewModel.updateStatusByAdmin(odId)
        }
    }
}