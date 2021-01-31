package com.istekno.coffeebreakapp.main.checkout

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseActivityViewModel
import com.istekno.coffeebreakapp.databinding.ActivityCheckoutBinding
import com.istekno.coffeebreakapp.main.payment.PaymentActivity
import com.istekno.coffeebreakapp.remote.ApiClient
import com.istekno.coffeebreakapp.utilities.Dialog
import com.istekno.coffeebreakapp.utilities.SharedPreferenceUtil
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.min

class CheckoutActivity : BaseActivityViewModel<ActivityCheckoutBinding, CheckoutViewModel>() {

    companion object {
        private val listDelivery = listOf("Dine in", "Door Delivery", "Pick up")
        private val listNow = listOf("Yes", "No")
    }

    private lateinit var sharedPref: SharedPreferenceUtil
    private lateinit var dialog: Dialog
    private var delivery = ""
    private var now = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_checkout
        setViewModel = ViewModelProvider(this).get(CheckoutViewModel::class.java)
        super.onCreate(savedInstanceState)

        val service = ApiClient.getApiClient(this)!!.create(CheckoutApiService::class.java)
        sharedPref = SharedPreferenceUtil(this)
        viewModel.setService(service)
        viewModel.setSharedPref(sharedPref)
        dialog = Dialog()
        binding.etCustomerAddress.setText(sharedPref.getPreference().acAddress)

        setChipGroup(binding.cgDeliveryMethod, listDelivery, 10)
        setChipGroup(binding.cgNow, listNow, 20)

        setInitialChecked()
        viewListener()
        subscribeLiveData()
    }

    @SuppressLint("ResourceType")
    private fun setInitialChecked() {
        val checkedIdDelivery = binding.cgDeliveryMethod.checkedChipId
        val checkedIdNow = binding.cgNow.checkedChipId
        val totalPrice = intent.getStringExtra("total_price")

        binding.tvTotalCost.text = totalPrice

        if (checkedIdDelivery == -1 || checkedIdNow == -1) {
            binding.cgDeliveryMethod.check(10)
            binding.cgNow.check(20)
            delivery = "Dine in"
            now = "Yes"
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun viewListener() {
        binding.cgDeliveryMethod.setOnCheckedChangeListener { _, checkedId ->
            val checkedIdNow = binding.cgNow.checkedChipId
            val chip: Chip = findViewById(checkedId)
            val id = chip.id
            delivery = chip.text.toString()

            if (id == 10) {
                if (checkedIdNow == 20) {
                    now = "Yes"
                    binding.tvSetTime.visibility = View.GONE
                    binding.etTimeReservation.visibility = View.GONE
                }
                binding.tvNow.visibility = View.VISIBLE
                binding.cgNow.visibility = View.VISIBLE
            } else {
                now = "No"
                binding.tvNow.visibility = View.GONE
                binding.cgNow.visibility = View.GONE
                binding.tvSetTime.visibility = View.VISIBLE
                binding.etTimeReservation.visibility = View.VISIBLE
            }
        }

        binding.cgNow.setOnCheckedChangeListener { _, checkedId ->
            val chip: Chip = findViewById(checkedId)
            val id = chip.id
            now = chip.text.toString()

            if (id == 21) {
                binding.tvSetTime.visibility = View.VISIBLE
                binding.etTimeReservation.visibility = View.VISIBLE
            } else {
                binding.tvSetTime.visibility = View.GONE
                binding.etTimeReservation.visibility = View.GONE
            }
        }

        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

        binding.etTimeReservation.setOnClickListener {
            val input = binding.etTimeReservation
            val calendar = Calendar.getInstance()
            val timePickerListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hour)
                calendar.set(Calendar.MINUTE, minute)
                input.setText(SimpleDateFormat("HH:mm:ss").format(calendar.time))
            }
            TimePickerDialog(this, timePickerListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show()
        }

        binding.etTimeReservation.setOnFocusChangeListener { _, b ->
            if (b) {
                val input = binding.etTimeReservation
                val calendar = Calendar.getInstance()
                val timePickerListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                    calendar.set(Calendar.HOUR_OF_DAY, hour)
                    calendar.set(Calendar.MINUTE, minute)
                    input.setText(SimpleDateFormat("HH:mm:ss").format(calendar.time))
                }
                TimePickerDialog(this, timePickerListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show()
            }
        }

        binding.btnConfirmAndPay.setOnClickListener {
            var etTimeReservation = binding.etTimeReservation.text.toString().trim()
            val setNow = now.map { it }[0].toString()
            val setDelivery = when (delivery) {
                "Dine in" -> "DI"
                "Door Delivery" -> "DD"
                "Pick up" -> "PU"
                else -> "null"
            }

            if (setNow == "N" && etTimeReservation.isEmpty()) {
                showToast("Time not set yet!")
                return@setOnClickListener
            } else if (setNow == "Y") {
                etTimeReservation = "00:00:00"
            }

            viewModel.addDelivery(setDelivery, setNow, etTimeReservation)
            intent<PaymentActivity>(this)
        }
    }

    private fun subscribeLiveData() {
        viewModel.isError.observe(this, {
            if (it) {
                viewModel.isMessage.observe(this, { str ->
                    showToast(str)
                })
            }
        })
    }

    private fun setChipGroup(chipGroup: ChipGroup, list: List<String>, type: Int) {
        for (element in list) {
            val chip = layoutInflater.inflate(R.layout.item_chipgroup_choice, chipGroup, false) as Chip

            chip.id = list.indexOf(element) + type
            chip.text = element
            chipGroup.addView(chip)
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}