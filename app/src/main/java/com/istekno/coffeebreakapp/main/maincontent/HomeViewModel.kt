package com.istekno.coffeebreakapp.main.maincontent

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class HomeViewModel: ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main
}