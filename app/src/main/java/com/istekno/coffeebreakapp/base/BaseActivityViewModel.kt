package com.istekno.coffeebreakapp.base

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.istekno.coffeebreakapp.utilities.Dialog

abstract class BaseActivityViewModel<ActivityBinding : ViewDataBinding, ActivityViewModel : ViewModel> :
    AppCompatActivity() {

    lateinit var binding: ActivityBinding
    lateinit var viewModel: ActivityViewModel
    protected var setLayout = 0
    protected var setViewModel: ActivityViewModel? = null
    private lateinit var dialog: Dialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, setLayout)
        viewModel = setViewModel!!
        dialog = Dialog()

        connectionCheck(this)
    }

    private fun connectionCheck(context: Context) {
        val connectManager = context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val checkNetwork = connectManager.activeNetworkInfo

        if (checkNetwork == null || !checkNetwork.isConnected || !checkNetwork.isAvailable) {
            dialog.dialogCheckInternet(this, this)
        }
    }

    protected inline fun <reified ActivityClass> intent(context: Context) {
        context.startActivity(Intent(context, ActivityClass::class.java))
    }
}