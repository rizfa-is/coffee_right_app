package com.istekno.coffeebreakapp.main.editpassword

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseActivityViewModel
import com.istekno.coffeebreakapp.databinding.ActivityEditPasswordBinding
import com.istekno.coffeebreakapp.main.editprofile.EditProfileViewModel
import com.istekno.coffeebreakapp.main.signup.SignupActivity
import com.istekno.coffeebreakapp.remote.ApiClient
import com.istekno.coffeebreakapp.utilities.SharedPreferenceUtil

class EditPasswordActivity : BaseActivityViewModel<ActivityEditPasswordBinding, EditPasswordViewModel>() {
    private lateinit var sharedPref: SharedPreferenceUtil

    companion object{
        const val FIELD_REQUIRED = "Field must not empty"
        const val PASS_NOT_MATCH = "Passwords must be the same"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_edit_password
        setViewModel = ViewModelProvider(this).get(EditPasswordViewModel::class.java)
        super.onCreate(savedInstanceState)
        sharedPref = SharedPreferenceUtil(this)

        setViewModel()
        subscribeLiveData()
        onClickListener()
    }

    private fun onClickListener(){
        binding.btnChangePassword.setOnClickListener {
            val acCurrentPassword = binding.etCurrentPassword.text.toString()
            val acPassword = binding.etNewPassword.text.toString()
            val acConfirmPassword = binding.etPasswordConfirm.text.toString()

            if (acCurrentPassword.isEmpty()) {
                showToast(FIELD_REQUIRED)
                return@setOnClickListener
            }
            if (acPassword.isEmpty()) {
                showToast(FIELD_REQUIRED)
                return@setOnClickListener
            }
            if (acPassword != acConfirmPassword) {
                showToast(PASS_NOT_MATCH)
                return@setOnClickListener
            }
            if (acPassword.length < 8) {
                showToast(SignupActivity.FIELD_LENGTH)
                return@setOnClickListener
            }
            if (sharedPref.getPreference().roleID != 0){
                viewModel.checkingPassword(
                    acId = sharedPref.getPreference().acID!!,
                    acPassword = acCurrentPassword
                )
                viewModel.onSuccessLiveData.observe(this@EditPasswordActivity, {
                    if (it) {
                        viewModel.changingPassword(
                            acId = sharedPref.getPreference().acID!!,
                            acPassword = acPassword
                        )
                    } else {
                        showToast("Wrong Current Password!")
                    }
                })
            }
        }

        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this).get(EditPasswordViewModel::class.java)
        viewModel.setService(createApi(this))
    }

    private fun subscribeLiveData() {
        viewModel.onSuccessLiveDataPassword.observe(this) {
            if (it) {
                setResult(RESULT_OK)
                this.finish()
            }
        }

    }

    private inline fun <reified ApiService> createApi(context: Context): ApiService {
        return ApiClient.getApiClient(context)!!.create(ApiService::class.java)
    }
    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}