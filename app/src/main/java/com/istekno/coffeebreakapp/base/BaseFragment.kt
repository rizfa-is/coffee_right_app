package com.istekno.coffeebreakapp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<FragmentBinding: ViewDataBinding>: Fragment()  {

    private lateinit var binding: FragmentBinding
    protected var setLayout = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, setLayout, container, false)
        return binding.root
    }
}