package com.istekno.coffeebreakapp.main.maincontent.orderhistory.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseActivity
import com.istekno.coffeebreakapp.base.BaseActivityViewModel
import com.istekno.coffeebreakapp.databinding.ActivityDetailOrderHistoryBinding
import com.istekno.coffeebreakapp.main.maincontent.order.OrderResponse
import com.istekno.coffeebreakapp.main.maincontent.orderhistory.OrderHistoryApiService
import com.istekno.coffeebreakapp.main.maincontent.orderhistory.OrderHistoryResponse
import com.istekno.coffeebreakapp.remote.ApiClient
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class DetailOrderHistoryActivity :
    BaseActivity<ActivityDetailOrderHistoryBinding>() {

    companion object {
        const val ORDER_HISTORY_KEY = "orID_KEY"
    }

    var listOrder = ArrayList<OrderHistoryResponse.Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_detail_order_history
        super.onCreate(savedInstanceState)

        val data = intent.getParcelableExtra<OrderHistoryResponse.Data>(ORDER_HISTORY_KEY)

        if (data != null) {
            setDataBinding(data)
        }

        setRecyclerView()
        onClickListener()
        (binding.rvListOrder.adapter as DetailOrderHistoryRecyclerViewAdapter).addList(data!!.productOrder)

    }

    @SuppressLint("SetTextI18n")
    private fun setDataBinding(model: OrderHistoryResponse.Data) {
        binding.model = model

        val dateTimeFormatted = dateTimeFormatter(model.orderUpdated).split('T')
        val dateUpdate = dateFormatter(dateTimeFormatted[0])
        val timeUpdate = dateTimeFormatted[1].split('+')[0]

        binding.tvOrderMethod.text = setDeliveryMethod(model.deliveryType)
        binding.tvDate.text = "$dateUpdate  -  $timeUpdate"
    }

    @SuppressLint("SimpleDateFormat", "NewApi")
    private fun dateFormatter(date: String): String {
        val myDate = LocalDate.parse(date)
        val formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy")
        val dateFormatted = myDate.format(formatter)

        return dateFormatted.replace("-".toRegex(), " ")
    }

    @SuppressLint("SimpleDateFormat", "NewApi")
    private fun dateTimeFormatter(date: String): String {
        val localZoneDateTime = ZonedDateTime.parse(date).withZoneSameInstant(ZoneId.systemDefault())

        return localZoneDateTime.toString()
    }

    private fun setDeliveryMethod(method: String): String {
        var delivery = ""
        when(method) {
            "DI" -> delivery = "Dine in"
            "DD" -> delivery = "Door Delivery"
            "PU" -> delivery = "Pick up"
        }

        return delivery
    }

    private fun onClickListener() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setRecyclerView() {
        binding.rvListOrder.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        val adapter = DetailOrderHistoryRecyclerViewAdapter(listOrder)
        binding.rvListOrder.adapter = adapter

    }
}