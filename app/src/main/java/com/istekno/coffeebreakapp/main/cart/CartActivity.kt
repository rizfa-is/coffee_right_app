package com.istekno.coffeebreakapp.main.cart

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseActivityViewModel
import com.istekno.coffeebreakapp.databinding.ActivityCartBinding
import com.istekno.coffeebreakapp.main.checkout.CheckoutActivity
import com.istekno.coffeebreakapp.remote.ApiClient
import com.istekno.coffeebreakapp.utilities.SharedPreferenceUtil

class CartActivity : BaseActivityViewModel<ActivityCartBinding, CartViewModel>() {

    private var listCart = ArrayList<CartResponse.DataCart>()

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_cart
        setViewModel = ViewModelProvider(this).get(CartViewModel::class.java)
        super.onCreate(savedInstanceState)

        val sharedPref = SharedPreferenceUtil(this)
        val service = ApiClient.getApiClient(this)?.create(CartApiService::class.java)
        if (service != null) {
            viewModel.setService(service)
        }
        viewModel.setSharedPref(sharedPref)
        viewModel.getListCartByCsId()
        viewModel.getListPriceCartByCsId()

        setRecyclerView()
        subscribeGetCartLiveData()
        subscribeLoadingLiveData()
        subscribeUpdateLiveData()
        onClickListener()

    }

    private fun subscribeUpdateLiveData() {
        viewModel.isUpdateCart.observe(this, Observer {
            if (it) {
                viewModel.isMessage.observe(this, Observer { msg->
                    Toast.makeText(this@CartActivity, msg , Toast.LENGTH_SHORT).show()
                })
            } else {
                viewModel.isMessage.observe(this, Observer { msg->
                    Toast.makeText(this@CartActivity, msg , Toast.LENGTH_SHORT).show()
                })
            }
        })
    }

    private fun subscribeLoadingLiveData() {
        viewModel.isLoading.observe(this, Observer {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })
    }

    private fun subscribeGetCartLiveData() {
        viewModel.isGetListCart.observe(this, Observer {
            if (it) {
                viewModel.listCart.observe(this, Observer { it1->
                    (binding.rvProductCart.adapter as CartAdapter).setData(it1)
                })
                binding.rvProductCart.visibility = View.VISIBLE
                viewModel.totalPriceCart.observe(this, Observer { price->
                    binding.tvTotal.text = price
                })
                binding.layoutTotal.visibility = View.VISIBLE
                binding.ivEmptyCart.visibility = View.GONE
                binding.tvNoCart.visibility = View.GONE
                binding.tvDescToAddCart.visibility = View.GONE
                binding.btnStartOrder.visibility = View.GONE
            } else {
                binding.layoutTotal.visibility = View.GONE
                binding.ivEmptyCart.visibility = View.VISIBLE
                binding.tvNoCart.visibility = View.VISIBLE
                binding.tvDescToAddCart.visibility = View.VISIBLE
                binding.btnStartOrder.visibility = View.VISIBLE
            }
        })
    }

    private fun setRecyclerView() {
        val rvAdapter = CartAdapter(listCart)

        binding.rvProductCart.apply {

            layoutManager = LinearLayoutManager(this@CartActivity, RecyclerView.VERTICAL, false)
            rvAdapter.setItems(listCart)
            binding.rvProductCart.adapter
            rvAdapter.notifyDataSetChanged()

            rvAdapter.plusItemCartClicked(object : CartAdapter.OnPlusItemCartClickCallBack {
                override fun onPlusItemCartClicked(cartModel: CartResponse.DataCart) {
                    Toast.makeText(this@CartActivity, "clicked plus", Toast.LENGTH_SHORT).show()
                    viewModel.updatePlusCartByOrId(cartModel.orderId)
                }
            })

            rvAdapter.minusItemCartClicked(object : CartAdapter.OnMinusCartClickCallBack {
                override fun minusItemCartClicked(cartModel: CartResponse.DataCart) {
                    Toast.makeText(this@CartActivity, "clicked minus", Toast.LENGTH_SHORT).show()
                    viewModel.updateMinusCartByOrId(cartModel.orderId)
                }
            })

            adapter = rvAdapter
        }
    }

    private fun onClickListener() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

        binding.fabGoCheckout.setOnClickListener {
            intent<CheckoutActivity>(this)
        }

    }
}