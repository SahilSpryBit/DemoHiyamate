package com.example.demohiyamate.activities

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.demohiyamate.R
import com.example.demohiyamate.model.CommonReq
import com.example.demohiyamate.model.ModelClass
import com.example.demohiyamate.network.ApiInterface
import com.example.demohiyamate.network.RetrofitInstance2
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Response

class HomeActivity : BaseActivity() {

    var commonApiRes: ModelClass? = null
    var sharedPreference : SharedPreferences? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment?

        val navController = navHostFragment!!.navController
        setupWithNavController(bottomNavigationView, navController)

        sharedPreference =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)

        commonApi()
    }

    private fun commonApi(){

        var deviceId = sharedPreference!!.getString("DEVICE_ID", "")
        val token = sharedPreference!!.getString("LOGIN_KEY", "")

        var commonReq = CommonReq("1.1", "")

        var retrofit = RetrofitInstance2.getInstance()
        var apiInterface = retrofit!!.create(ApiInterface::class.java)

        val data = apiInterface.commonApi(token, "2", deviceId, commonReq)
        progressDialogUtil.showProgressDialog("Loading...")

        data!!.enqueue(object : retrofit2.Callback<ModelClass?>{
            override fun onResponse(call: Call<ModelClass?>, response: Response<ModelClass?>) {
                if(response.isSuccessful){
                    if(response.body()!!.status == true){
                        progressDialogUtil.hideProgressDialog()
                        /*   dialogUtil.showDialog(response.body()!!.message)   */
                        commonApiRes = response.body()

                        ID_Status = commonApiRes!!.data?.STRIPEIDVERIFICATIONSTATUS.toString()
                        ID_Color = commonApiRes!!.data?.STRIPEIDVERIFICATIONSTATUSCOLORCODE.toString()

                    }else{
                        progressDialogUtil.hideProgressDialog()
                        dialogUtil.showAuthorizationDialog(response.body()!!.message, true)
                    }

                }
            }

            override fun onFailure(call: Call<ModelClass?>, t: Throwable) {
                progressDialogUtil.hideProgressDialog()
                dialogUtil.showDialog(t.localizedMessage)
            }


        })

    }
}