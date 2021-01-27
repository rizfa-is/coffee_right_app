package com.istekno.coffeebreakapp.main.payment

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.denzcoskun.imageslider.models.SlideModel
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseActivityViewModel
import com.istekno.coffeebreakapp.databinding.ActivityPaymentBinding
import com.istekno.coffeebreakapp.main.maincontent.order.OrderFragment

class PaymentActivity : BaseActivityViewModel<ActivityPaymentBinding, PaymentViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_payment
        setViewModel = ViewModelProvider(this).get(PaymentViewModel::class.java)
        super.onCreate(savedInstanceState)

        setImageSlider()

        onClickListener()

    }

    private fun onClickListener() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnPayNow.setOnClickListener {
//            intent<OrderFragment>(this)
        }
    }

    private fun setImageSlider() {
        val imageList = ArrayList<SlideModel>()

        imageList.add(SlideModel(R.drawable.img_card_payment))
        imageList.add(SlideModel(R.drawable.img_bank_account_payment))
        imageList.add(SlideModel(R.drawable.img_cod_payment))

        binding.imageSliderPaymentMethod.setImageList(imageList)
    }
}