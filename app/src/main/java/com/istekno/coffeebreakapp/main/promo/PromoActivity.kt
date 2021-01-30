package com.istekno.coffeebreakapp.main.promo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseActivityViewModel
import com.istekno.coffeebreakapp.databinding.ActivityPromoBinding
import com.istekno.coffeebreakapp.main.detailproduct.DetailProductActivity
import com.istekno.coffeebreakapp.main.maincontent.homepage.HomeFragment
import com.istekno.coffeebreakapp.main.maincontent.homepage.HomeResponse
import com.istekno.coffeebreakapp.main.maincontent.homepage.HomeService
import com.istekno.coffeebreakapp.remote.ApiClient

class PromoActivity : BaseActivityViewModel<ActivityPromoBinding, PromoViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_promo
        setViewModel = ViewModelProvider(this).get(PromoViewModel::class.java)
        super.onCreate(savedInstanceState)

        val service = ApiClient.getApiClient(this)?.create(HomeService::class.java)
        if (service != null) {
            viewModel.setService(service)
        }

        viewModel.getAllProduct()
        setRecyclerView()
        subscribeLiveData()
        onClickListener()
    }

    private fun onClickListener() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setRecyclerView() {
        binding.rvPromo.apply {
            val rvAdapter = PromoAdapter()
            rvAdapter.notifyDataSetChanged()
            val gridLayoutManager =
                GridLayoutManager(this@PromoActivity, 2, LinearLayoutManager.VERTICAL, false)
            layoutManager = gridLayoutManager

            rvAdapter.setOnItemClicked(object : PromoAdapter.OnItemClickCallback {
                override fun onItemClicked(productModel: HomeResponse.DataProduct) {
                    val sendIntent = Intent(this@PromoActivity, DetailProductActivity::class.java)
                    sendIntent.putExtra(HomeFragment.HOME_KEY, productModel.productId)
                    startActivity(sendIntent)
                }
            })
            adapter = rvAdapter
        }
    }


    private fun subscribeLiveData() {
        viewModel.isLoading.observe(this) {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
                binding.rvPromo.visibility = View.GONE
            } else {
                binding.progressBar.visibility = View.GONE
                binding.rvPromo.visibility = View.VISIBLE
            }
        }

        viewModel.listData.observe(this) { data ->
            val mutablePromo: MutableList<HomeResponse.DataProduct> = data.toMutableList()
            mutablePromo.removeIf { it.discountId == 0 || it.discountId == 1 }

            (binding.rvPromo.adapter as PromoAdapter).setData(mutablePromo)

        }
    }

}