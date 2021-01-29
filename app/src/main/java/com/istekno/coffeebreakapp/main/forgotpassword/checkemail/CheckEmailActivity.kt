/*
package com.istekno.coffeebreakapp.main.forgotpassword.checkemail

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.istekno.coffeebreakapp.R

class CheckEmailActivity : BaseActivityCoroutine<ActivityVerifyEmailBinding>(), View.OnClickListener {
    private lateinit var viewModel: CheckEmailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_check_email
        super.onCreate(savedInstanceState)

        initTextWatcher()
        setViewModel()
        subscribeLiveData()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_next -> {
                when {
                    !valEmail(binding.inputLayoutEmail, binding.etEmail) -> {}
                    else -> {
                        viewModel.serviceApi(
                            email = binding.etEmail.text.toString()
                        )
                    }
                }
            }
            R.id.ln_back -> {
                this@CheckEmailActivity.finish()
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

    private fun subscribeLiveData() {
        viewModel.isLoadingLiveData.observe(this@CheckEmailActivity, {
            binding.btnNext.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
        })

        viewModel.onSuccessLiveData.observe(this@CheckEmailActivity, {
            if (it) {
                binding.progressBar.visibility = View.GONE
                binding.btnNext.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
                binding.btnNext.visibility = View.VISIBLE
            }
        })

        viewModel.onSetAccountId.observe(this@CheckEmailActivity, {
            val intent = Intent(this@CheckEmailActivity, ResetPasswordActivity::class.java)
            intent.putExtra("ac_id", it)
            startActivity(intent)
            this@CheckEmailActivity.finish()
        })

        viewModel.onFailLiveData.observe(this@CheckEmailActivity, {
            noticeToast(it)
        })
    }

    inner class MyTextWatcher(private val view: View) : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun afterTextChanged(editable: Editable) {
            when (view.id) {
                R.id.et_email -> valEmail(binding.inputLayoutEmail, binding.etEmail)
            }
        }
    }
}
*/
