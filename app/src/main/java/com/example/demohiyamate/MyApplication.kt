package com.example.demohiyamate

import android.app.Application
import android.content.Context


class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        mContext = applicationContext
    }

    companion object {
        private var mContext: Context? = null
        val appContext: Context?
            get() = mContext
    }
}