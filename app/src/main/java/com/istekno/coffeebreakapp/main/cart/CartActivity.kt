package com.istekno.coffeebreakapp.main.cart

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseActivityViewModel
import com.istekno.coffeebreakapp.databinding.ActivityCartBinding

class CartActivity : BaseActivityViewModel<ActivityCartBinding, CartViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_cart
        setViewModel = ViewModelProvider(this).get(CartViewModel::class.java)
        super.onCreate(savedInstanceState)
    }
}