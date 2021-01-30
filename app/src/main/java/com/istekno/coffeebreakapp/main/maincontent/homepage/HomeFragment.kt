package com.istekno.coffeebreakapp.main.maincontent.homepage

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseFragmentViewModel
import com.istekno.coffeebreakapp.databinding.FragmentHomeBinding
import com.istekno.coffeebreakapp.main.detailproduct.DetailProductActivity
import com.istekno.coffeebreakapp.main.favorite.FavoriteActivity
import com.istekno.coffeebreakapp.main.promo.PromoActivity
import com.istekno.coffeebreakapp.remote.ApiClient

class HomeFragment(private val toolbar: MaterialToolbar, private val title: TextView, private val navDrawer: NavigationView) : BaseFragmentViewModel<FragmentHomeBinding, HomeViewModel>() {

    companion object {
        const val HOME_KEY = "home_key"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setLayout = R.layout.fragment_home
        setView()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        super.onViewCreated(view, savedInstanceState)
        navDrawer.setCheckedItem(R.id.nav_home)
        val service = ApiClient.getApiClient(view.context)!!.create(HomeService::class.java)

        viewModel.setService(service)
        viewModel.getAllProduct()

        setRecyclerView(view)
        subscribeLiveData()
        viewListener(view)
    }

    private fun viewListener(view: View) {
        binding.tvHomeSeemore1.setOnClickListener {
            intent<FavoriteActivity>(view.context)
        }

        binding.tvHomeSeemore2.setOnClickListener {
            intent<PromoActivity>(view.context)
        }
    }

    private fun setRecyclerView(view: View) {
        binding.rvFavorite.apply {
            val rvAdapter = HomeAdapter()
            rvAdapter.notifyDataSetChanged()
            layoutManager = LinearLayoutManager(view.context, RecyclerView.HORIZONTAL, false)

            rvAdapter.setOnItemClicked(object : HomeAdapter.OnItemClickCallback {
                override fun onItemClicked(productModel: HomeResponse.DataProduct) {
                    val sendIntent = Intent(view.context, DetailProductActivity::class.java)
                    sendIntent.putExtra(HOME_KEY, productModel.productId)
                    startActivity(sendIntent)
                }
            })
            adapter = rvAdapter
        }

        binding.rvPromo.apply {
            val rvAdapter = HomeAdapter()
            rvAdapter.notifyDataSetChanged()
            layoutManager = LinearLayoutManager(view.context, RecyclerView.HORIZONTAL, false)

            rvAdapter.setOnItemClicked(object : HomeAdapter.OnItemClickCallback {
                override fun onItemClicked(productModel: HomeResponse.DataProduct) {
                    val sendIntent = Intent(view.context, DetailProductActivity::class.java)
                    sendIntent.putExtra(HOME_KEY, productModel.productId)
                    startActivity(sendIntent)
                }
            })
            adapter = rvAdapter
        }
    }

    private fun subscribeLiveData() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.rvFavorite.visibility = View.INVISIBLE
                binding.pgFavorite.visibility = View.VISIBLE
                binding.rvPromo.visibility = View.INVISIBLE
                binding.pgPromoe.visibility = View.VISIBLE
            } else {
                binding.rvFavorite.visibility = View.VISIBLE
                binding.pgFavorite.visibility = View.INVISIBLE
                binding.rvPromo.visibility = View.VISIBLE
                binding.pgPromoe.visibility = View.INVISIBLE
            }
        }

        viewModel.listData.observe(viewLifecycleOwner) { data ->
            val mutableFavorite: MutableList<HomeResponse.DataProduct> = data.toMutableList()
            val mutablePromo: MutableList<HomeResponse.DataProduct> = data.toMutableList()

            mutableFavorite.removeIf { it.productFavorite == "N" }
            mutablePromo.removeIf { it.discountId == 1 }

            (binding.rvFavorite.adapter as HomeAdapter).setData(mutableFavorite)
            (binding.rvPromo.adapter as HomeAdapter).setData(mutablePromo)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setView() {
        toolbar.menu.findItem(R.id.toolbar_cart).isVisible = true
        toolbar.menu.findItem(R.id.toolbar_search).isVisible = true

        title.text = "Home"
    }
}