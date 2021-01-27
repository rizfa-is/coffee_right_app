package com.istekno.coffeebreakapp.main.payment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.models.SlideModel
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseActivityViewModel
import com.istekno.coffeebreakapp.databinding.ActivityPaymentBinding
import com.istekno.coffeebreakapp.main.maincontent.MainContentActivity
import com.istekno.coffeebreakapp.main.orderhistory.detail.DetailOrderHistoryRecyclerViewAdapter
import com.istekno.coffeebreakapp.remote.ApiClient
import com.istekno.coffeebreakapp.utilities.SharedPreferenceUtil

class PaymentActivity : BaseActivityViewModel<ActivityPaymentBinding, PaymentViewModel>() {

    var listCart = ArrayList<PaymentModel>()
    var paymentMethod: String = ""
    private lateinit var sharePref: SharedPreferenceUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_payment
        setViewModel = ViewModelProvider(this).get(PaymentViewModel::class.java)
        super.onCreate(savedInstanceState)
        sharePref = SharedPreferenceUtil(this)
        val service = ApiClient.getApiClient(this)?.create(PaymentApiService::class.java)
        if (service != null) {
            viewModel.setService(service)
        }

        onRadioButtonClicked(binding.root)

        setRecyclerView()
        viewModel.callApiService()
        subscribeLiveData()
        onClickListener()
    }

    fun onClickListener() {
        binding.btnPayNow.setOnClickListener {
            showToast("Peek-a-Boo")
//            val id = sharePref.getPreference().roleID
//            viewModel.createOrderDetailApi(id!!, paymentMethod, "Paid")
        }
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.radio_card ->
                    if (checked) {
                        paymentMethod = "Card"
                    }
                R.id.radio_bank_account ->
                    if (checked) {
                        paymentMethod = "Bank"
                    }
                R.id.radio_cod ->
                    if (checked) {
                        paymentMethod = "COD"
                    }
            }
        }
    }

    fun subscribeLiveData() {
        viewModel.isLoading.observe(this) {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
                binding.scrollView.visibility = View.GONE
            } else {
                binding.progressBar.visibility = View.GONE
                binding.scrollView.visibility = View.VISIBLE
            }
        }
        viewModel.listData.observe(this) {
            (binding.rvListOrder.adapter as PaymentRecyclerViewAdapter).addList(it)
        }
        viewModel.totalPrice.observe(this) {
            val tax = (it * 0.1).toInt()
            val subTotal = it
            val totalPrice = tax + subTotal

            binding.tvSubtotal.text = subTotal.toString()
            binding.tvTax.text = tax.toString()
            binding.tvTotal.text = totalPrice.toString()
        }
        viewModel.isCreateOrderDetail.observe(this) {
            if (it) {
                showToast("order processed successfully")
                val intent = Intent(this, MainContentActivity::class.java)
                intent.putExtra("data", 0)
                startActivity(intent)
            } else {
                showToast("Failed to process order")
            }
        }
    }

    private fun setRecyclerView() {
        binding.rvListOrder.isNestedScrollingEnabled = false
        binding.rvListOrder.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        val adapter = PaymentRecyclerViewAdapter(listCart)
        binding.rvListOrder.adapter = adapter
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}