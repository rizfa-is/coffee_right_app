package com.istekno.coffeebreakapp.main.maincontent.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.MaterialToolbar
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseFragmentViewModel
import com.istekno.coffeebreakapp.databinding.FragmentProfileBinding
import com.istekno.coffeebreakapp.main.editprofile.EditProfileActivity

class ProfileFragment(private val toolbar: MaterialToolbar, private val title: TextView) : BaseFragmentViewModel<FragmentProfileBinding, ProfileViewModel>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setLayout = R.layout.fragment_profile
        setView()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        super.onViewCreated(view, savedInstanceState)
        onClickListener()
    }

    fun onClickListener(){
        binding.btnEditProfile.setOnClickListener{
            val intent = Intent(requireContext(),EditProfileActivity::class.java)
            startActivity(intent)
        }
    }
    @SuppressLint("SetTextI18n")
    private fun setView() {
        toolbar.menu.findItem(R.id.toolbar_cart).isVisible = false
        toolbar.menu.findItem(R.id.toolbar_search).isVisible = false

        title.text = "My Profile"
    }
}