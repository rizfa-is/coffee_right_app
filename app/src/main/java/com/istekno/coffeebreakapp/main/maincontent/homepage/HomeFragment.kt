package com.istekno.coffeebreakapp.main.maincontent.homepage

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.MaterialToolbar
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseFragmentViewModel
import com.istekno.coffeebreakapp.databinding.FragmentHomeBinding

class HomeFragment(private val toolbar: MaterialToolbar, private val title: TextView) : BaseFragmentViewModel<FragmentHomeBinding, HomeViewModel>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setLayout = R.layout.fragment_home
        setView()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        super.onViewCreated(view, savedInstanceState)
    }

    @SuppressLint("SetTextI18n")
    private fun setView() {
        toolbar.menu.findItem(R.id.toolbar_cart).isVisible = true
        toolbar.menu.findItem(R.id.toolbar_search).isVisible = true

        title.text = "Home"
    }
}