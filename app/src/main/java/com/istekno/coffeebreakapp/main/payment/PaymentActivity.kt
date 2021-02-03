package com.istekno.coffeebreakapp.main.payment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseActivityViewModel
import com.istekno.coffeebreakapp.databinding.ActivityPaymentBinding
import com.istekno.coffeebreakapp.main.maincontent.mainactivity.MainContentActivity
import com.istekno.coffeebreakapp.remote.ApiClient
import com.istekno.coffeebreakapp.utilities.Dialog
import com.istekno.coffeebreakapp.utilities.SharedPreferenceUtil
import java.text.DecimalFormat

class PaymentActivity : BaseActivityViewModel<ActivityPaymentBinding, PaymentViewModel>() {

    private var listCart = ArrayList<PaymentModel>()
    private var paymentMethod: String = ""
    private lateinit var sharePref: SharedPreferenceUtil
    private lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_payment
        setViewModel = ViewModelProvider(this).get(PaymentViewModel::class.java)
        super.onCreate(savedInstanceState)
        sharePref = SharedPreferenceUtil(this)
        val service = ApiClient.getApiClient(this)?.create(PaymentApiService::class.java)
        if (service != null) {
            viewModel.setService(service)
        }

        dialog = Dialog()

        binding.radioCard.isClickable = false
        binding.radioBankAccount.isClickable = false
        binding.radioCod.isChecked = true

        val customerId = sharePref.getPreference().roleID
        setRecyclerView()
        viewModel.callApiService(customerId!!)
        subscribeLiveData()
        subscribeSuccessLiveData()
        onClickListener()

    }


    override fun onBackPressed() {
        super.onBackPressed()
        val customerId = sharePref.getPreference().roleID
        viewModel.deleteDelivery(customerId!!)
    }

    private fun onClickListener() {
        binding.btnPayNow.setOnClickListener {
            val customerId = sharePref.getPreference().roleID

//            if (paymentMethod == "") {
//                showToast("Please choose payment method!")
//                return@setOnClickListener
//            }
            dialog.dialog(this, "Are You Sure ?") { viewModel.createOrderDetailApi(customerId!!, "COD", "Unpaid") }
        }

        binding.ivBack.setOnClickListener {
            val customerId = sharePref.getPreference().roleID
            viewModel.deleteDelivery(customerId!!)
            onBackPressed()
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

    @SuppressLint("SetTextI18n")
    private fun subscribeLiveData() {
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
            val formatter = DecimalFormat("#,###")
            val tax = (it * 0.1).toInt()
            val subTotal = it
            val totalPrice = tax + subTotal

            binding.tvSubtotal.text = "IDR ${formatter.format(subTotal.toDouble())}"
            binding.tvTax.text = "IDR ${formatter.format(tax.toDouble())}"
            binding.tvTotal.text = "IDR ${formatter.format(totalPrice.toDouble())}"
        }
    }

    private fun subscribeSuccessLiveData() {
        viewModel.isProcessSuccess.observe(this) {
            if (it) {
                viewModel.isUpdateSuccess.observe(this, Observer { update ->
                    if (update) {
                        val intent = Intent(this, MainContentActivity::class.java)
                        intent.putExtra("data", 0)
                        startActivity(intent)
                        finishAffinity()
                    }
                })
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
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

}