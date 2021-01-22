package com.istekno.coffeebreakapp.main.detailproduct

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseActivityViewModel
import com.istekno.coffeebreakapp.databinding.ActivityDetailProductBinding

class DetailProductActivity : BaseActivityViewModel<ActivityDetailProductBinding, DetailProductViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_detail_product
        setViewModel = ViewModelProvider(this).get(DetailProductViewModel::class.java)
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        )

    }
}