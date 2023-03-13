package com.example.demohiyamate.fragments

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.icu.number.NumberFormatter.with
import android.media.MediaScannerConnection
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.with
import com.example.demohiyamate.MyApplication
import com.example.demohiyamate.R
import com.example.demohiyamate.activities.BaseActivity
import com.example.demohiyamate.activities.BusinessProfileActivity
import com.example.demohiyamate.activities.HomeActivity
import com.example.demohiyamate.activities.SettingActivity
import com.example.demohiyamate.model.GetCompyInfoRes
import com.example.demohiyamate.network.ApiInterface
import com.example.demohiyamate.network.RetrofitInstance2
import com.google.android.gms.cast.framework.media.ImagePicker
import kotlinx.coroutines.NonCancellable.start
import retrofit2.Call
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


class MoreFragment : Fragment() {

    var img_profile : ImageView? = null
    var username: TextView? = null
    var userABN: TextView? = null
    var address: TextView? = null
    var txt_status: TextView? = null

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MoreFragment().apply {
            }
    }

    lateinit var activity: HomeActivity
    lateinit var baseActivity: BaseActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_more, container, false)

         username = view.findViewById<TextView>(R.id.username)
         userABN = view.findViewById<TextView>(R.id.userABN)
         address = view.findViewById<TextView>(R.id.address)
         txt_status = view.findViewById<TextView>(R.id.txt_status)
        img_profile = view.findViewById<ImageView>(R.id.img_profile)
        val card_image = view.findViewById<CardView>(R.id.card_image)
        val setting_layout = view.findViewById<LinearLayout>(R.id.setting_layout)
        val businessProfile = view.findViewById<LinearLayout>(R.id.businessProfile)

        val sharedPreference =  requireContext().getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)

        var deviceId = sharedPreference.getString("DEVICE_ID", "")
        var token = sharedPreference.getString("LOGIN_KEY", "")

        activity = requireActivity() as HomeActivity
        baseActivity = requireActivity() as BaseActivity

        img_profile!!.setOnClickListener {
           /* showPictureDialog()*/

        }

        if (token != null) {
            getcompanyDetailsApi(token)
        }

        setting_layout.setOnClickListener {

            var intent = Intent(context, SettingActivity::class.java)
            startActivity(intent)
        }

        businessProfile.setOnClickListener {

            var intent = Intent(context, BusinessProfileActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    private fun getcompanyDetailsApi(token :String){

        var retrofit = RetrofitInstance2.getInstance()
        var apiInterface = retrofit!!.create(ApiInterface::class.java)

        val data = apiInterface.getCompanyUserInfo(token)

        Log.d("Testinggg", token!!)

        baseActivity.progressDialogUtil.showProgressDialog("Loading...")

        data!!.enqueue(object : retrofit2.Callback<GetCompyInfoRes?>{
            override fun onResponse(
                call: Call<GetCompyInfoRes?>,
                response: Response<GetCompyInfoRes?>
            ) {
                if(response.isSuccessful){
                    if(response.body()!!.status == true){
                            baseActivity.progressDialogUtil.hideProgressDialog()

                        Log.d("Testinggg", "Successss" + response.body())
                        username!!.text = response.body()!!.data?.company?.name
                        userABN!!.text = "ABN " + response.body()!!.data?.company?.abn
                        address!!.text = response.body()!!.data?.address?.address
                        Glide.with(MyApplication.appContext!!).load(response.body()!!.data?.company?.picture.toString())
                            .placeholder(R.drawable.no_image_available)
                            .error(R.drawable.no_image_available)
                            .into(img_profile!!)
                        txt_status!!.text = activity.ID_Status
                        txt_status!!.setTextColor(Color.parseColor(activity.ID_Color))

                    }else{
                        /*  dialogUtil.showDialog(response.body()!!.message)*/
                        Log.d("Testinggg", "Else : "+ response.body()!!.message)
                    }
                }else{
                    /* dialogUtil.showDialog(response.body()!!.message)*/
                    Log.d("Testinggg", "Elesuccess : "+ response.body()!!.message)
                }
            }

            override fun onFailure(call: Call<GetCompyInfoRes?>, t: Throwable) {
                /*   dialogUtil.showDialog(t.localizedMessage)*/
                Log.d("Testinggg", "Errorrr  "+ t.localizedMessage)
            }

        })
    }
}