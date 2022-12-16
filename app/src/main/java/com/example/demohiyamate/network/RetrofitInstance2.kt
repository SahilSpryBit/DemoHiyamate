package com.example.demohiyamate.network

import com.chuckerteam.chucker.api.Chucker
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.demohiyamate.MyApplication
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance2 {

    private var retrofit: Retrofit? = null
    private var httpClient : OkHttpClient? = null
    private val BASEURL = "http://workunderconstruction.com/demo/hiyamate-dev/staging/backend/"

    fun getInstance(): Retrofit {

        httpClient = OkHttpClient()

        httpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val requestBuilder = chain.request().newBuilder()
                requestBuilder.addHeader("content-type", "application/json")
                chain.proceed(requestBuilder.build())
            }
            .readTimeout(2, TimeUnit.MINUTES)
            .writeTimeout(2, TimeUnit.MINUTES)
            .connectTimeout(1, TimeUnit.MINUTES)
            .addInterceptor( ChuckerInterceptor(MyApplication.appContext!!))
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()


        return Retrofit.Builder().baseUrl(BASEURL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create()) .build()



    }
}