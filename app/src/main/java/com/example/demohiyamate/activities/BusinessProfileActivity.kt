package com.example.demohiyamate.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.demohiyamate.R
import com.example.demohiyamate.network.ApiInterface
import com.example.demohiyamate.network.RetrofitInstance2
import com.google.android.material.bottomsheet.BottomSheetBehavior

class BusinessProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_business_profile)

        var retrofit = RetrofitInstance2.getInstance()
        var apiInterface = retrofit!!.create(ApiInterface::class.java)

    }
}