package com.istekno.coffeebreakapp.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel

abstract class BaseActivityViewModel<ActivityBinding: ViewDataBinding, ActivityViewModel: ViewModel>: AppCompatActivity() {

    lateinit var binding: ActivityBinding
    lateinit var viewModel: ActivityViewModel
    protected var setLayout = 0
    protected var setViewModel: ActivityViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, setLayout)
        viewModel = setViewModel!!
    }

    protected inline fun <reified ActivityClass> intent(context: Context) {
        context.startActivity(Intent(context, ActivityClass::class.java))
    }
}