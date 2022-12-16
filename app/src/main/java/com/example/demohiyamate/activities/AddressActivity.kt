package com.example.demohiyamate.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import com.example.demohiyamate.MyApplication
import com.example.demohiyamate.R
import com.example.demohiyamate.adapter.AddressesSearchResultAdapter
import com.example.demohiyamate.model.google.Prediction
import com.example.demohiyamate.model.google.SearchAddressWithGoogle
import com.example.demohiyamate.network.ApiInterface
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.ArrayList
import java.util.concurrent.TimeUnit

class AddressActivity : AppCompatActivity() {

    lateinit var apiService: ApiInterface
    var searchview : SearchView? = null
    var descriptionList: ArrayList<Prediction> = ArrayList<Prediction>()
    lateinit var dataAdapter: AddressesSearchResultAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        setContentView(R.layout.activity_address)

        searchview = findViewById(R.id.searchview)
        var txtCancel = findViewById<TextView>(R.id.txtCancel)
        var listView1 = findViewById<ListView>(R.id.listView1)

        searchview?.requestFocus()
        retrofitInit()

        txtCancel.setOnClickListener {
            finish()
        }

        searchview?.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                SearchApi(newText, listView1)
               return true
            }
        })
    }

    private fun SearchApi(value: String?, listview: ListView){

        apiService.searchAddressByGoogleApi(value)?.enqueue(object : Callback<SearchAddressWithGoogle?>{
            override fun onResponse(
                call: Call<SearchAddressWithGoogle?>,
                response: retrofit2.Response<SearchAddressWithGoogle?>
            ) {

                if(response.isSuccessful){
                    if(response.body()!!.status.equals("OK")){

                        descriptionList = response.body()!!.predictions
                        if (descriptionList.size != 0) {
                            dataAdapter = AddressesSearchResultAdapter(this@AddressActivity, descriptionList)
                            listview.setAdapter(dataAdapter)
                            dataAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<SearchAddressWithGoogle?>, t: Throwable) {

            }


        })
    }

    private fun retrofitInit() {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(object : Interceptor {
                @Throws(IOException::class)
                override fun intercept(chain: Interceptor.Chain): Response {
                    val requestBuilder = chain.request().newBuilder()
                    requestBuilder.addHeader("Accept", "application/json")
                    return chain.proceed(requestBuilder.build())
                }
            })
            .readTimeout(2, TimeUnit.MINUTES)
            .writeTimeout(2, TimeUnit.MINUTES)
            .connectTimeout(2, TimeUnit.MINUTES)
            .build()
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl("https://maps.googleapis.com/maps/api/place/autocomplete/")
            .client(httpClient)
            .build()
        apiService = retrofit.create(ApiInterface::class.java)
    }
}