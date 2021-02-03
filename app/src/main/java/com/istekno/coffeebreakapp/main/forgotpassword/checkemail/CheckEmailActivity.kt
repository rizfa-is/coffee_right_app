package com.istekno.coffeebreakapp.main.forgotpassword.checkemail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseActivityViewModel
import com.istekno.coffeebreakapp.databinding.ActivityCheckEmailBinding
import com.istekno.coffeebreakapp.main.forgotpassword.checkemail.ValidateAccount.Companion.valEmail
import com.istekno.coffeebreakapp.main.forgotpassword.resetpassword.ResetPasswordActivity
import com.istekno.coffeebreakapp.remote.ApiClient

class CheckEmailActivity : BaseActivityViewModel<ActivityCheckEmailBinding, CheckEmailViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_check_email
        setViewModel = ViewModelProvider(this).get(CheckEmailViewModel::class.java)
        super.onCreate(savedInstanceState)

        initTextWatcher()
        setViewModel()
        subscribeLiveData()
        onClickListener()
    }

    private fun onClickListener() {
        binding.btnResendLink.setOnClickListener {
            when {
                !valEmail(binding.tiEmail, binding.etEmail) -> {
                }
                else -> {
                    viewModel.serviceApi(
                        email = binding.etEmail.text.toString()
                    )
                }
            }
        }
    }

    private fun initTextWatcher() {
        binding.etEmail.addTextChangedListener(MyTextWatcher(binding.etEmail))
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this@CheckEmailActivity).get(CheckEmailViewModel::class.java)
        viewModel.setService(createApi(this@CheckEmailActivity))
    }

    private inline fun <reified ApiService> createApi(context: Context): ApiService {
        return ApiClient.getApiClient(context)!!.create(ApiService::class.java)
    }

    private fun subscribeLiveData() {
        viewModel.isLoadingLiveData.observe(this@CheckEmailActivity, {
            binding.btnResendLink.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
        })

        viewModel.onSuccessLiveData.observe(this@CheckEmailActivity, {
            if (it) {
                binding.progressBar.visibility = View.GONE
                binding.btnResendLink.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
                binding.btnResendLink.visibility = View.VISIBLE
            }
        })

        viewModel.onSetAccountId.observe(this@CheckEmailActivity, {
            val intent = Intent(this@CheckEmailActivity, ResetPasswordActivity::class.java)
            intent.putExtra("ac_id", it)
            startActivity(intent)
            this@CheckEmailActivity.finish()
        })

        viewModel.onFailLiveData.observe(this@CheckEmailActivity, {
            showToast(it)
        })
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    inner class MyTextWatcher(private val view: View) : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun afterTextChanged(editable: Editable) {
            when (view.id) {
                R.id.et_email -> valEmail(binding.tiEmail, binding.etEmail)
            }
        }
    }
}
