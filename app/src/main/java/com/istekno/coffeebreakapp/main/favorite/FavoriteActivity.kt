package com.istekno.coffeebreakapp.main.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseActivityViewModel
import com.istekno.coffeebreakapp.databinding.ActivityFavoriteBinding
import com.istekno.coffeebreakapp.main.detailproduct.DetailProductActivity
import com.istekno.coffeebreakapp.main.maincontent.homepage.HomeAdapter
import com.istekno.coffeebreakapp.main.maincontent.homepage.HomeFragment
import com.istekno.coffeebreakapp.main.maincontent.homepage.HomeResponse
import com.istekno.coffeebreakapp.main.maincontent.homepage.HomeService
import com.istekno.coffeebreakapp.remote.ApiClient

class FavoriteActivity : BaseActivityViewModel<ActivityFavoriteBinding, FavoriteViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_favorite
        setViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        super.onCreate(savedInstanceState)

        val service = ApiClient.getApiClient(this)?.create(HomeService::class.java)
        if (service != null) {
            viewModel.setService(service)
        }

        viewModel.getAllProduct()
        setRecyclerView()
        subscribeLiveData()


    }

    private fun setRecyclerView() {
        binding.rvDrink.apply {
            val rvAdapter = HomeAdapter()
            rvAdapter.notifyDataSetChanged()
            val gridLayoutManager = GridLayoutManager(this@FavoriteActivity, 2, LinearLayoutManager.HORIZONTAL, false)
            layoutManager = gridLayoutManager

            rvAdapter.setOnItemClicked(object : HomeAdapter.OnItemClickCallback {
                override fun onItemClicked(productModel: HomeResponse.DataProduct) {
                    val sendIntent = Intent(this@FavoriteActivity, DetailProductActivity::class.java)
                    sendIntent.putExtra(HomeFragment.HOME_KEY, productModel.productId)
                    startActivity(sendIntent)
                }
            })
            adapter = rvAdapter
        }

        binding.rvFood.apply {
            val rvAdapter = HomeAdapter()
            rvAdapter.notifyDataSetChanged()
            layoutManager = LinearLayoutManager(this@FavoriteActivity, RecyclerView.HORIZONTAL, false)

            rvAdapter.setOnItemClicked(object : HomeAdapter.OnItemClickCallback {
                override fun onItemClicked(productModel: HomeResponse.DataProduct) {
                    val sendIntent = Intent(this@FavoriteActivity, DetailProductActivity::class.java)
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
                binding.scrollView.visibility = View.GONE
            } else {
                binding.progressBar.visibility = View.GONE
                binding.scrollView.visibility = View.VISIBLE
            }
        }

        viewModel.listData.observe(this) { data ->
            val mutableDrink: MutableList<HomeResponse.DataProduct> = data.toMutableList()
            val mutableFood: MutableList<HomeResponse.DataProduct> = data.toMutableList()

            mutableDrink.removeIf { it.productFavorite == "N" || it.productCategory == "Food" }
            mutableFood.removeIf { it.productFavorite == "N" || it.productCategory == "Drink" }

            (binding.rvDrink.adapter as HomeAdapter).setData(mutableDrink)
            (binding.rvFood.adapter as HomeAdapter).setData(mutableFood)
        }
    }
}