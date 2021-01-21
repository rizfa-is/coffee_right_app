package com.istekno.coffeebreakapp.main.maincontent

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.appbar.MaterialToolbar
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseFragment
import com.istekno.coffeebreakapp.databinding.FragmentSettingBinding

class SettingFragment(private val toolbar: MaterialToolbar, private val title: TextView) : BaseFragment<FragmentSettingBinding>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setLayout = R.layout.fragment_setting
        setView()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    @SuppressLint("SetTextI18n")
    private fun setView() {
        toolbar.menu.findItem(R.id.toolbar_cart).isVisible = false
        toolbar.menu.findItem(R.id.toolbar_search).isVisible = false

        title.text = "Setting"
    }
}