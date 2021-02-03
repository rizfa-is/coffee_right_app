package com.istekno.coffeebreakapp.base

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.istekno.coffeebreakapp.utilities.Dialog

abstract class BaseActivity<ActivityBinding : ViewDataBinding> : AppCompatActivity() {
    protected lateinit var binding: ActivityBinding
    protected var setLayout = 0
    private lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, setLayout)
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