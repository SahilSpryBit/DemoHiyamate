package com.example.demohiyamate.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.demohiyamate.MyApplication
import com.example.demohiyamate.R
import com.example.demohiyamate.model.GetCompyInfoRes
import com.example.demohiyamate.model.ModelClass
import com.example.demohiyamate.network.ApiInterface
import com.example.demohiyamate.network.RetrofitInstance
import com.example.demohiyamate.network.RetrofitInstance2
import com.example.demohiyamate.viewmodels.SplashViewModel
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class SplashActivity : BaseActivity() {

    lateinit var handler : Handler
    lateinit var splashViewModel : SplashViewModel

    var sharedPreference : SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        sharedPreference =  getSharedPreferences("PREFERENCE_NAME",Context.MODE_PRIVATE)
        var login_key = sharedPreference!!.getString("LOGIN_KEY", "")

        handler = Handler()

      /*  handler.postDelayed({
            if(login_key!!.isEmpty()){

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }

        }, 3000 )*/

        splashViewModel = ViewModelProvider(this).get(SplashViewModel::class.java)

        val dataa: Uri? = intent.data
        if (dataa == null) {

            splashViewModel.start()
        }

        splashViewModel.splashlivedata.observe(this, Observer {

            if(it == true){
                if(login_key!!.isEmpty()){
                    val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    val intent = Intent(this@SplashActivity, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        })
        BaseUrlApi()
    }

    private fun BaseUrlApi(){

        var retrofit = RetrofitInstance.getInstance()
        var apiInterface = retrofit!!.create(ApiInterface::class.java)
        val data = apiInterface.getBaseURL("2", "1.0", "2")
        data!!.enqueue(object : retrofit2.Callback<ModelClass?>{
            override fun onResponse(call: Call<ModelClass?>, response: Response<ModelClass?>) {

                if(response.isSuccessful){
                    if(response.body()!!.status == true){

                        var baseurlapi : String = response.body()!!.data?.baseUrl.toString()
                        Log.d("Testinggg", ""+baseurlapi)
                        var deviceId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)

                        val editor: SharedPreferences.Editor = sharedPreference!!.edit()
                        editor.putString("BASE_URL_KEY", baseurlapi)
                        editor.putString("DEVICE_ID", deviceId)
                        editor.apply()
                        editor.commit()
                        Log.d("Testinggg", "Base Url : "+sharedPreference!!.getString("BASE_URL_KEY",""))

                    }

                }else{

                    Log.d("Testinggg", "Elseee")
                }
            }

            override fun onFailure(call: Call<ModelClass?>, t: Throwable) {
                Log.d("Testinggg", "Failll "+t.localizedMessage)
            }

        })
    }

   /* fun testAPI(){

        val sharedPreference =  this.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)

        var deviceId = sharedPreference.getString("DEVICE_ID", "")
        var token = sharedPreference.getString("LOGIN_KEY", "")

        var retrofit = RetrofitInstance2.getInstance()
        var apiInterface = retrofit!!.create(ApiInterface::class.java)

        token = "113ca1a26fddb54f5f8f4e81eb3dd3d77f5c7f9d85cfca45271bc1feccb2daeb99023048c8e778c4009b8df6813d4cce8696d1e79aa774cee6d88948e36d65b9sTOYm6MMeD87exMSrOFVp9uplMfN7vBxLOySldQqzdGRVOZ0YEpvoTzBxclp3ZlXul//ttmIR6RBhXcWPEFcBhIq7NtRXKljeHLNxg64jsTOdPs8FI91mOw9rjc3uoXeDlaPDGynfkQHnc4dSJjorA=="

        val data = apiInterface.getCompanyUserInfo(token)

        Log.d("Testinggg", token!!)

        *//*     BaseActivity.progressDialogUtil.showProgressDialog("Loading...")*//*

        data!!.enqueue(object : retrofit2.Callback<GetCompyInfoRes?>{
            override fun onResponse(
                call: Call<GetCompyInfoRes?>,
                response: Response<GetCompyInfoRes?>
            ) {
                if(response.isSuccessful){
                    if(response.body()!!.status == true){
                        *//*    progressDialogUtil.hideProgressDialog()
                            dialogUtil.showDialog(response.body()!!.message)*//*
                        Log.d("Testinggg", "Successss")
                        Toast.makeText(this@SplashActivity, "Success", Toast.LENGTH_LONG).show()

                    }else{
                        *//*  dialogUtil.showDialog(response.body()!!.message)*//*
                        Log.d("Testinggg", "Else : "+ response.body()!!.message)
                        Toast.makeText(this@SplashActivity, "Else : "+ response.body()!!.message, Toast.LENGTH_LONG).show()
                    }
                }else{
                    *//* dialogUtil.showDialog(response.body()!!.message)*//*
                    Log.d("Testinggg", "Elesuccess : "+ response.body()!!.message)
                    Toast.makeText(this@SplashActivity, "Elesuccess : "+ response.body()!!.message, Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<GetCompyInfoRes?>, t: Throwable) {
                *//*   dialogUtil.showDialog(t.localizedMessage)*//*
                Log.d("Testinggg", "Errorrr  "+ t.localizedMessage)
            }

        })

    }*/
}


