package com.example.demohiyamate.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class SplashViewModel() : ViewModel() , CoroutineScope {

    var splashlivedata : MutableLiveData<Boolean> = MutableLiveData()
    var istimeOver = false

    fun start(){

         launch(Dispatchers.Main) {
            delay(3000)
            istimeOver = true
            splashlivedata.postValue(true)
        }

    }


    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main


}