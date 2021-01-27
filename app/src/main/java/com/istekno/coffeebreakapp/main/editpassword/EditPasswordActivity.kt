package com.istekno.coffeebreakapp.main.editpassword

import android.os.Bundle
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseActivityViewModel
import com.istekno.coffeebreakapp.databinding.ActivityEditPasswordBinding

class EditPasswordActivity : BaseActivityViewModel<ActivityEditPasswordBinding, EditPasswordViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_edit_password
        setViewModel = ViewModelProvider(this).get(EditPasswordViewModel::class.java)
        super.onCreate(savedInstanceState)
    }
}