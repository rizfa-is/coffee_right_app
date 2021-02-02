package com.istekno.coffeebreakapp.main.favorite

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseActivity
import com.istekno.coffeebreakapp.base.BaseActivityViewModel
import com.istekno.coffeebreakapp.databinding.ActivityFavoriteBinding
import com.istekno.coffeebreakapp.main.detailproduct.DetailProductActivity
import com.istekno.coffeebreakapp.main.maincontent.homepage.HomeFavoriteAdapter
import com.istekno.coffeebreakapp.main.maincontent.homepage.HomeFragment
import com.istekno.coffeebreakapp.main.maincontent.homepage.GetProductResponse
import com.istekno.coffeebreakapp.main.maincontent.homepage.HomeService
import com.istekno.coffeebreakapp.remote.ApiClient

class FavoriteActivity : BaseActivity<ActivityFavoriteBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_favorite
        super.onCreate(savedInstanceState)

        setRecyclerView()
        setFavoriteData(binding.rvDrink, "Food")
        setFavoriteData(binding.rvFood, "Drink")
        onClickListener()
    }

    private fun onClickListener() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setRecyclerView() {
        binding.rvDrink.apply {
            val rvAdapter = HomeFavoriteAdapter()
            rvAdapter.notifyDataSetChanged()
            val gridLayoutManager =
                GridLayoutManager(this@FavoriteActivity, 2, LinearLayoutManager.HORIZONTAL, false)
            layoutManager = gridLayoutManager

            rvAdapter.setOnItemClicked(object : HomeFavoriteAdapter.OnItemClickCallback {
                override fun onItemClicked(productModel: GetProductResponse.DataProduct) {
                    val sendIntent =
                        Intent(this@FavoriteActivity, DetailProductActivity::class.java)
                    sendIntent.putExtra(HomeFragment.HOME_KEY, productModel.productId)
                    startActivity(sendIntent)
                }
            })
            adapter = rvAdapter
        }

        binding.rvFood.apply {
            val rvAdapter = HomeFavoriteAdapter()
            rvAdapter.notifyDataSetChanged()
            layoutManager =
                LinearLayoutManager(this@FavoriteActivity, RecyclerView.HORIZONTAL, false)

            rvAdapter.setOnItemClicked(object : HomeFavoriteAdapter.OnItemClickCallback {
                override fun onItemClicked(productModel: GetProductResponse.DataProduct) {
                    val sendIntent =
                        Intent(this@FavoriteActivity, DetailProductActivity::class.java)
                    sendIntent.putExtra(HomeFragment.HOME_KEY, productModel.productId)
                    startActivity(sendIntent)
                }
            })
            adapter = rvAdapter
        }
    }

    private fun setFavoriteData(recyclerView: RecyclerView, filter: String) {
        val favorite = intent.extras?.getParcelableArray("favorite")?.toMutableList()
        val mutable = mutableListOf<GetProductResponse.DataProduct>()

        favorite?.map { mutable.add(it as GetProductResponse.DataProduct) }
        mutable.removeIf { it.productCategory == filter }

        (recyclerView.adapter as HomeFavoriteAdapter).setData(mutable)
    }
}