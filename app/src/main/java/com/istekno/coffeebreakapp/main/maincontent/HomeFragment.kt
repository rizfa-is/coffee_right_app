package com.istekno.coffeebreakapp.main.maincontent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.base.BaseFragment
import com.istekno.coffeebreakapp.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setLayout = R.layout.fragment_home
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}