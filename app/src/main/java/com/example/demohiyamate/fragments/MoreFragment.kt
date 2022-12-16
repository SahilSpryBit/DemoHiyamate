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

                        Log.d("Testinggg", "Successss")
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

    /* fun showPictureDialog() {
      *//*  val pictureDialog = AlertDialog.Builder(MyApplication.appContext!!)
        pictureDialog.setTitle("Select Action")
        val pictureDialogItems = arrayOf("Select photo from gallery", "Capture photo from camera")
        pictureDialog.setItems(pictureDialogItems
        ) { dialog, which ->
            when (which) {
                0 -> choosePhotoFromGallary()
                1 -> takePhotoFromCamera()
            }
        }
         pictureDialog.create().show()*//*

         choosePhotoFromGallary()
    }*/

/*    fun choosePhotoFromGallary() {
        val galleryIntent = Intent(Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        startActivityForResult(galleryIntent, 21)
    }

    private fun takePhotoFromCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, 31)
    }

     override fun onActivityResult(requestCode:Int, resultCode:Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        *//* if (resultCode == this.RESULT_CANCELED)
         {
         return
         }*//*
        if (requestCode == 21)
        {
            if (data != null)
            {
                val contentURI = data!!.data
                try
                {
                    val bitmap = MediaStore.Images.Media.getBitmap(MyApplication.appContext!!.contentResolver, contentURI)
                    val path = saveImage(bitmap)
                    Toast.makeText(MyApplication.appContext!!, "Image Saved!", Toast.LENGTH_SHORT).show()
                    img_profile!!.setImageBitmap(bitmap)

                }
                catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(MyApplication.appContext!!, "Failed!", Toast.LENGTH_SHORT).show()
                }

            }

        }
        else if (requestCode == 31)
        {
            val thumbnail = data!!.extras!!.get("data") as Bitmap
          *//*  imageview!!.setImageBitmap(thumbnail)*//*
            saveImage(thumbnail)
            Toast.makeText(MyApplication.appContext!!, "Image Saved!", Toast.LENGTH_SHORT).show()
        }
    }

    fun saveImage(myBitmap: Bitmap):String {
        val bytes = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
        val wallpaperDirectory = File(
            (Environment.getExternalStorageDirectory()).toString() + "/sahil")
        // have the object build the directory structure, if needed.
        Log.d("fee",wallpaperDirectory.toString())
        if (!wallpaperDirectory.exists())
        {

            wallpaperDirectory.mkdirs()
        }

        try
        {
            Log.d("heel",wallpaperDirectory.toString())
            val f = File(wallpaperDirectory, ((Calendar.getInstance()
                .getTimeInMillis()).toString() + ".jpg"))
            f.createNewFile()
            val fo = FileOutputStream(f)
            fo.write(bytes.toByteArray())
            MediaScannerConnection.scanFile(MyApplication.appContext!!,
                arrayOf(f.getPath()),
                arrayOf("image/jpeg"), null)
            fo.close()
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath())

            return f.getAbsolutePath()
        }
        catch (e1: IOException) {
            e1.printStackTrace()
        }

        return ""
    }*/

}