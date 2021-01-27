package com.istekno.coffeebreakapp.main.checkout

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseActivity
import com.istekno.coffeebreakapp.base.BaseActivityViewModel
import com.istekno.coffeebreakapp.databinding.ActivityCheckoutBinding
import com.istekno.coffeebreakapp.utilities.Dialog
import com.istekno.coffeebreakapp.utilities.SharedPreferenceUtil

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

        sharedPref = SharedPreferenceUtil(this)
        dialog = Dialog()
        binding.etCustomerAddress.setText(sharedPref.getPreference().acAddress)

        setChipGroup(binding.cgDeliveryMethod, listDelivery, 10)
        setChipGroup(binding.cgNow, listNow, 20)

        setInitialChecked()
        viewListener()
    }

    @SuppressLint("ResourceType")
    private fun setInitialChecked() {
        val checkedIdDelivery = binding.cgDeliveryMethod.checkedChipId
        val checkedIdNow = binding.cgNow.checkedChipId

        if (checkedIdDelivery == -1 || checkedIdNow == -1) {
            binding.cgDeliveryMethod.check(10)
            binding.cgNow.check(20)
            delivery = "Dine in"
            now = "Yes"
        }
    }

    private fun viewListener() {
        binding.cgDeliveryMethod.setOnCheckedChangeListener { _, checkedId ->
            val checkedIdNow = binding.cgNow.checkedChipId
            val chip: Chip = findViewById(checkedId)
            val id = chip.id
            delivery = chip.text.toString()

            if (id == 10) {
                if (checkedIdNow == 20) {
                    binding.tvSetTime.visibility = View.GONE
                    binding.etTimeReservation.visibility = View.GONE
                }
                binding.tvNow.visibility = View.VISIBLE
                binding.cgNow.visibility = View.VISIBLE
            } else {
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

        binding.btnConfirmAndPay.setOnClickListener {
            val time = binding.etTimeReservation.text.toString().trim()
            val message = "Delivery Method: $delivery \nNow: $now \nSet time: $time"

            dialog.dialogCancel(this, message) { DialogInterface.BUTTON_NEGATIVE }
        }
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