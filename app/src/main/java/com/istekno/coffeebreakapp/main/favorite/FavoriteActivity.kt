package com.istekno.coffeebreakapp.main.favorite

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseActivityViewModel
import com.istekno.coffeebreakapp.databinding.ActivityFavoriteBinding

class FavoriteActivity : BaseActivityViewModel<ActivityFavoriteBinding, FavoriteViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_favorite
        setViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        super.onCreate(savedInstanceState)
    }
}