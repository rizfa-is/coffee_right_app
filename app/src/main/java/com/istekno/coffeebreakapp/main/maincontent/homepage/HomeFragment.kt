package com.istekno.coffeebreakapp.main.maincontent.homepage

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
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
import com.istekno.coffeebreakapp.utilities.SharedPreferenceUtil

class HomeFragment(
    private val title: TextView,
    private val navDrawer: NavigationView
) : BaseFragmentViewModel<FragmentHomeBinding, HomeViewModel>() {

    companion object {
        const val HOME_KEY = "home_key"
    }

    private var listFavorite = arrayOf<GetProductResponse.DataProduct>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setLayout = R.layout.fragment_home
        setView()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        super.onViewCreated(view, savedInstanceState)
        navDrawer.setCheckedItem(R.id.nav_home)
        val service = ApiClient.getApiClient(view.context)!!.create(HomeService::class.java)
        val sharedPref = SharedPreferenceUtil(view.context)

        viewModel.setService(service)
        viewModel.setSharedPref(sharedPref)
        viewModel.getListCartByCsId()
        viewModel.getAllProduct()

        setRecyclerView(view)
        subscribeLiveData()
        viewListener(view)
    }

    private fun viewListener(view: View) {
        binding.tvHomeSeemore1.setOnClickListener {
            val sendIntent = Intent(context, FavoriteActivity::class.java)
            val bundle = Bundle()

            bundle.putParcelableArray("favorite", listFavorite)
            sendIntent.putExtras(bundle)
            startActivity(sendIntent)
        }

        binding.tvHomeSeemore2.setOnClickListener {
            intent<PromoActivity>(view.context)
        }
    }

    private fun setRecyclerView(view: View) {
        binding.rvFavorite.apply {
            val rvAdapter = HomeFavoriteAdapter()
            rvAdapter.notifyDataSetChanged()
            layoutManager = LinearLayoutManager(view.context, RecyclerView.HORIZONTAL, false)

            rvAdapter.setOnItemClicked(object : HomeFavoriteAdapter.OnItemClickCallback {
                override fun onItemClicked(productModel: GetProductResponse.DataProduct) {
                    val sendIntent = Intent(view.context, DetailProductActivity::class.java)
                    sendIntent.putExtra(HOME_KEY, productModel.productId)
                    startActivity(sendIntent)
                }
            })
            adapter = rvAdapter
        }

        binding.rvPromo.apply {
            val rvAdapter = HomePromoAdapter()
            rvAdapter.notifyDataSetChanged()
            layoutManager = LinearLayoutManager(view.context, RecyclerView.HORIZONTAL, false)

            rvAdapter.setOnItemClicked(object : HomePromoAdapter.OnItemClickCallback {
                override fun onItemClicked(productModel: GetProductResponse.DataProduct) {
                    val sendIntent = Intent(view.context, DetailProductActivity::class.java)
                    sendIntent.putExtra(HOME_KEY, productModel.productId)
                    startActivity(sendIntent)
                }
            })
            adapter = rvAdapter
        }
    }

    private fun subscribeLiveData() {
        viewModel.listFavorite.observe(viewLifecycleOwner) { favorite ->
            listFavorite = favorite.toTypedArray()

            for (i in favorite.indices) {
                if (i >= 5) {
                    favorite.removeLast()
                }
            }

            (binding.rvFavorite.adapter as HomeFavoriteAdapter).setData(favorite)
        }

        viewModel.listPromo.observe(viewLifecycleOwner, { promo ->
            for (i in 0 until promo.size) {
                if (i >= 5) {
                    promo.removeLast()
                }
            }

            (binding.rvPromo.adapter as HomePromoAdapter).setData(promo)
        })

        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.rvFavorite.visibility = View.INVISIBLE
                binding.pgFavorite.visibility = View.VISIBLE
                binding.rvPromo.visibility = View.INVISIBLE
                binding.pgPromoe.visibility = View.VISIBLE

                binding.linebot.visibility = View.VISIBLE
            } else {
                binding.rvFavorite.visibility = View.VISIBLE
                binding.pgFavorite.visibility = View.INVISIBLE
                binding.rvPromo.visibility = View.VISIBLE
                binding.pgPromoe.visibility = View.INVISIBLE

                binding.linebot.visibility = View.GONE
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setView() {
        title.text = "Home"
    }
}