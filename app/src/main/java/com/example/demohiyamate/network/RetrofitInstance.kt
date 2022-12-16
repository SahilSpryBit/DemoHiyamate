package com.example.demohiyamate.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private var retrofit: Retrofit? = null
    private val BASEURL = "https://workunderconstruction.com/demo/hiyamate-dev/staging/backend/"

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())

            .build()
    }
}