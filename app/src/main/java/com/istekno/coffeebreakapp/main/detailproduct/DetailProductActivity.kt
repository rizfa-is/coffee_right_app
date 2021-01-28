package com.istekno.coffeebreakapp.main.detailproduct

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.bumptech.glide.Glide
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseActivityViewModel
import com.istekno.coffeebreakapp.databinding.ActivityDetailProductBinding
import com.istekno.coffeebreakapp.main.cart.CartActivity
import com.istekno.coffeebreakapp.remote.ApiClient
import com.istekno.coffeebreakapp.utilities.SharedPreferenceUtil
import java.text.NumberFormat
import java.util.*

class DetailProductActivity : BaseActivityViewModel<ActivityDetailProductBinding, DetailProductViewModel>() {

    companion object {
        const val HOME_KEY = "home_key"
        const val img = "http://184.72.105.243:3000/images/"
    }

    private lateinit var sharePref: SharedPreferenceUtil
    var prID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_detail_product
        setViewModel = ViewModelProvider(this).get(DetailProductViewModel::class.java)
        super.onCreate(savedInstanceState)

        sharePref = SharedPreferenceUtil(this)
        val service = ApiClient.getApiClient(this)?.create(DetailProductApiService::class.java)
        if (service != null) {
            viewModel.setService(service)
        }

        prID = intent.getIntExtra(HOME_KEY, -1)
        viewModel.getProductDetail(prID)
        subscribeLiveData()
        onClickListener()
    }

    private fun onClickListener() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

        binding.ivCart.setOnClickListener {
            intent<CartActivity>(this)
        }

        binding.btnAddCart.setOnClickListener {
            val customerId = sharePref.getPreference().roleID
            viewModel.createOrder(prID, customerId!!)
            subscribeCreateOrder()
        }
    }

    private fun subscribeLiveData() {
        viewModel.isLoading.observe(this) {
            if (it) {
                binding.scrollView.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
                binding.scrollView.visibility = View.VISIBLE
            }
        }

        viewModel.listData.observe(this) {
            val price = NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(it[0].productPrice.toDouble())
                .replace("Rp".toRegex(),"IDR ")
            binding.model = it[0]
            binding.tvPrice.text = price
            Glide.with(binding.root).load(img + it[0].productImage)
                .placeholder(R.drawable.error)
                .error(R.drawable.ic_profile).into(binding.ivProduct)

        }
    }

    private fun subscribeCreateOrder() {
        viewModel.isCreateSuccess.observe(this) {
            if (it) {
                intent<CartActivity>(this)
            } else {
                Toast.makeText(this, "Add to cart failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}