package com.istekno.coffeebreakapp.main.promo

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseActivityViewModel
import com.istekno.coffeebreakapp.databinding.ActivityPromoBinding

class PromoActivity : BaseActivityViewModel<ActivityPromoBinding, PromoViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_promo
        setViewModel = ViewModelProvider(this).get(PromoViewModel::class.java)
        super.onCreate(savedInstanceState)
    }
}