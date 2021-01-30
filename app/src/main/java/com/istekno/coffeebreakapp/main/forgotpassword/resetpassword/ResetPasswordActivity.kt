package com.istekno.coffeebreakapp.main.forgotpassword.resetpassword

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseActivityViewModel
import com.istekno.coffeebreakapp.databinding.ActivityResetPasswordBinding
import com.istekno.coffeebreakapp.main.forgotpassword.checkemail.ValidateAccount.Companion.valPassConf
import com.istekno.coffeebreakapp.main.forgotpassword.checkemail.ValidateAccount.Companion.valPassword
import com.istekno.coffeebreakapp.remote.ApiClient

class ResetPasswordActivity : BaseActivityViewModel<ActivityResetPasswordBinding, ResetPasswordViewModel>() {
    private var acId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_reset_password
        setViewModel = ViewModelProvider(this).get(ResetPasswordViewModel::class.java)
        super.onCreate(savedInstanceState)
        acId = intent.getIntExtra("ac_id", 0)

        initTextWatcher()
        setViewModel()
        subscribeLiveData()
        onClickListener()
    }

    private fun onClickListener() {
            binding.btnResetPassword.setOnClickListener {
                when {
                    !valPassword(binding.inputLayoutNewPassword, binding.etNewPassword) -> {}
                    !valPassConf(
                        binding.inputLayoutPasswordConfirm,
                        binding.etPasswordConfirm,
                        binding.etNewPassword
                    ) -> {}
                    else -> {
                        viewModel.serviceUpdate(
                            acId = acId!!,
                            acPassword = binding.etNewPassword.text.toString()
                        )
                    }
                }
            }
            binding.ivBack.setOnClickListener {
                onBackPressed()
            }
    }

    private fun initTextWatcher() {
        binding.etNewPassword.addTextChangedListener(MyTextWatcher(binding.etNewPassword))
        binding.etPasswordConfirm.addTextChangedListener(MyTextWatcher(binding.etPasswordConfirm))
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this@ResetPasswordActivity).get(ResetPasswordViewModel::class.java)
        viewModel.setService(createApi(this@ResetPasswordActivity))
    }
    private inline fun <reified ApiService> createApi(context: Context): ApiService {
        return ApiClient.getApiClient(context)!!.create(ApiService::class.java)
    }

    private fun subscribeLiveData() {
        viewModel.isLoadingLiveData.observe(this@ResetPasswordActivity, {
            binding.btnResetPassword.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
        })

        viewModel.onSuccessLiveData.observe(this@ResetPasswordActivity, {
            if (it) {
                binding.progressBar.visibility = View.GONE
                binding.btnResetPassword.visibility = View.VISIBLE

                this@ResetPasswordActivity.finish()
            } else {
                binding.progressBar.visibility = View.GONE
                binding.btnResetPassword.visibility = View.VISIBLE
            }
        })

        viewModel.onMessageLiveData.observe(this@ResetPasswordActivity, {
            showToast(it)
        })

        viewModel.onFailLiveData.observe(this@ResetPasswordActivity, {
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
                R.id.et_password -> valPassword(
                    binding.inputLayoutNewPassword,
                    binding.etNewPassword
                )
                R.id.et_password_confirm -> valPassConf(
                    binding.inputLayoutPasswordConfirm,
                    binding.etPasswordConfirm,
                    binding.etNewPassword
                )
            }
        }
    }
}
