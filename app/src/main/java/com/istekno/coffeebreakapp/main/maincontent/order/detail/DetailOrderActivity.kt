package com.istekno.coffeebreakapp.main.maincontent.order.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseActivityViewModel
import com.istekno.coffeebreakapp.databinding.ActivityDetailOrderBinding
import com.istekno.coffeebreakapp.main.maincontent.mainactivity.MainContentActivity
import com.istekno.coffeebreakapp.main.maincontent.order.OrderApiService
import com.istekno.coffeebreakapp.main.maincontent.orderhistory.detail.DetailOrderHistoryModel
import com.istekno.coffeebreakapp.main.maincontent.orderhistory.detail.DetailOrderHistoryRecyclerViewAdapter
import com.istekno.coffeebreakapp.remote.ApiClient
import com.istekno.coffeebreakapp.utilities.SharedPreferenceUtil

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

        val sharedPref = SharedPreferenceUtil(this)
        val service = ApiClient.getApiClient(this)?.create(OrderApiService::class.java)
        if (service != null) {
            viewModel.setService(service)
        }

        val id = intent.getIntExtra(ORDER_HISTORY_KEY, -1)

        if (sharedPref.getPreference().level == 0) {
            binding.btnUpdateDone.visibility = View.GONE
        } else {
            binding.btnUpdateDone.visibility = View.VISIBLE
        }

        setRecyclerView()
        viewModel.callOrderApiService(id)
        subscribeLoadingLiveData()
        subscribeGetListLiveData()
        subscribeUpdateLiveData()
        onClickListener(id)
    }

    private fun subscribeUpdateLiveData() {
        viewModel.isUpdate.observe(this, Observer {
            if (it) {
                Toast.makeText(this, "Updated successfully!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainContentActivity::class.java)
//                intent.putExtra("data", 0)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Failed to update!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun subscribeLoadingLiveData() {
        viewModel.isLoading.observe(this, {
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
                    binding.tvSubtotal.text = intent.getIntExtra(PRICE_BEFORE_TAX, -1).toString()
                    binding.tvTotal.text = intent.getIntExtra(TOTAL_PRICE, -1).toString()
                })
            } else {
                Toast.makeText(this, "Something wrong ...", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun onClickListener(odId : Int) {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnUpdateDone.setOnClickListener {
            viewModel.updateStatusByAdmin(odId)
        }
    }
}