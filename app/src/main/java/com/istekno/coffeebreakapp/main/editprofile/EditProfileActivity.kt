package com.istekno.coffeebreakapp.main.editprofile

import android.os.Bundle
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseActivityViewModel
import com.istekno.coffeebreakapp.databinding.ActivityEditProfileBinding

class EditProfileActivity : BaseActivityViewModel<ActivityEditProfileBinding, EditProfileViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_edit_profile
        setViewModel = ViewModelProvider(this).get(EditProfileViewModel::class.java)
        super.onCreate(savedInstanceState)
    }
}