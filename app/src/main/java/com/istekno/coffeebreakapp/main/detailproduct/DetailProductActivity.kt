package com.istekno.coffeebreakapp.main.detailproduct

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseActivityViewModel
import com.istekno.coffeebreakapp.databinding.ActivityDetailProductBinding
import com.istekno.coffeebreakapp.main.cart.CartActivity
import com.istekno.coffeebreakapp.remote.ApiClient

class DetailProductActivity : BaseActivityViewModel<ActivityDetailProductBinding, DetailProductViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_detail_product
        setViewModel = ViewModelProvider(this).get(DetailProductViewModel::class.java)
        super.onCreate(savedInstanceState)

        val service = ApiClient.getApiClient(this)?.create(DetailProductApiService::class.java)
        if (service != null) {
            viewModel.setService(service)
        }


        window.setFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        )


        viewModel.getProductDetail(2)
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
    }

    fun subscribeLiveData() {
        viewModel.isLoading.observe(this, Observer {
            if (it) {
                binding.scrollView.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
                binding.scrollView.visibility = View.VISIBLE
            }
        })

        viewModel.listData.observe(this, Observer {
            binding.model = it[0]
            val img = "http://184.72.105.243:3000/images/"
            Glide.with(binding.root).load(img + it[0].productImage)
                    .placeholder(R.drawable.error)
                    .error(R.drawable.ic_profile).into(binding.ivProduct)

        })
    }
}