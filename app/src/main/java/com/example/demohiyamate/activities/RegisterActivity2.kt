package com.example.demohiyamate.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.provider.Telephony.Mms.Addr
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.*
import androidx.core.content.ContextCompat
import com.example.demohiyamate.R
import com.example.demohiyamate.model.Category
import com.example.demohiyamate.model.GetCategoryRes
import com.example.demohiyamate.model.ModelClass
import com.example.demohiyamate.model.google.GetState
import com.example.demohiyamate.model.google.latlon.GetLatLongById
import com.example.demohiyamate.model.login.Address
import com.example.demohiyamate.model.login.Company
import com.example.demohiyamate.model.login.LoginResponseModel
import com.example.demohiyamate.model.login.User
import com.example.demohiyamate.model.register.RegisterResponse
import com.example.demohiyamate.network.ApiInterface
import com.example.demohiyamate.network.RetrofitInstance2
import com.google.android.flexbox.FlexboxLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.hiyamate.model.register.request.AddressX
import com.hiyamate.model.register.request.RegisterRequest
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class RegisterActivity2 : BaseActivity() {

    var str_firstname = ""
    var str_lastname = ""
    var str_mobile = ""
    var str_email = ""
    var tax_file_number = ""
    var str_password = ""
    var str_dob = ""
    var str_business_type = ""

    var latitude = ""
    var city = ""
    var longitude = ""
    var postalCode = ""
    var suburb = ""
    var state = ""

    var state_id = ""
    var placeId = ""
    var addressDiscription = ""

    var sheetBehavior: BottomSheetBehavior<*>? = null
    lateinit var bottom_sheet: LinearLayout
    lateinit var rl_gray_bg: FrameLayout
    var flexService: FlexboxLayout? = null

    lateinit var mContext: Context
    lateinit var lst_selected_trade: ArrayList<Category>
    lateinit var lst_selected_temp: ArrayList<Category>
    lateinit var lst_trade: ArrayList<Category>

    lateinit var apiService: ApiInterface
    private val FIR_PLACE_LOCATION_FROM = 1

    lateinit var lst_state: ArrayList<GetState>

    var edtBusinessAddress: TextInputEditText? = null

    private var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white))
        }
        setContentView(R.layout.activity_register2)

        bottom_sheet = findViewById<LinearLayout>(R.id.bottom_sheet)
        sheetBehavior = BottomSheetBehavior.from<LinearLayout>(bottom_sheet)
        bottom_sheet.bringToFront()
        rl_gray_bg = findViewById<FrameLayout>(R.id.rl_bg)

        val sharedPreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)

        var deviceId = sharedPreference.getString("DEVICE_ID", "")

        mContext = this@RegisterActivity2
        lst_selected_trade = ArrayList()
        lst_selected_temp = ArrayList()
        lst_trade = ArrayList()
        lst_state = ArrayList()

        sharedPreferences = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)

        var btnregister = findViewById<Button>(R.id.btnRegister)
        var edtBusinessName = findViewById<TextInputEditText>(R.id.edtBusinessName)
        var edtABN = findViewById<TextInputEditText>(R.id.edtABN)
        var edtService = findViewById<TextInputEditText>(R.id.edtService)
        edtBusinessAddress = findViewById<TextInputEditText>(R.id.edtBusinessAddress)
        var imageViewback = findViewById<ImageView>(R.id.imageViewback)
        var spnrBusinessType = findViewById<Spinner>(R.id.spnrBusinessType)
        var txtInpABN = findViewById<TextInputLayout>(R.id.txtInpABN)
        var txtInpService = findViewById<TextInputLayout>(R.id.txtInpService)
        var lnr_abn_msg = findViewById<LinearLayout>(R.id.lnr_abn_msg)
        var linear_trade = findViewById<LinearLayout>(R.id.linear_trade)
        var txtDone = findViewById<TextView>(R.id.txtDone)
        var txt_close = findViewById<TextView>(R.id.txt_close)
        flexService = findViewById(R.id.flexService)

        var intent = intent.extras

        if (intent != null) {
            str_firstname = intent.getString("first_name")!!
            str_lastname = intent.getString("last_name", "")
            str_mobile = intent.getString("mobile", "")
            str_email = intent.getString("email", "")
            tax_file_number = intent.getString("tax_file_number", "")
            str_password = intent.getString("password", "")
            str_dob = intent.getString("dob", "")
        }

        imageViewback.setOnClickListener {
            onBackPressed()
        }

        retrofitInit()

        txtDone.setOnClickListener {
            lst_selected_trade.clear()
            lst_selected_trade.addAll(lst_selected_temp)
            lst_selected_temp.clear()
            if (lst_selected_trade.size > 0) {
                flexService!!.visibility = View.VISIBLE
                txtInpService.visibility = View.GONE

                setTradeSelectedTextview()
            } else {
                flexService!!.visibility = View.GONE
                txtInpService.visibility = View.VISIBLE
                setTradeSelectedTextview()
            }
            if (sheetBehavior!!.state == BottomSheetBehavior.STATE_EXPANDED) {
                sheetBehavior!!.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }

        sheetBehavior!!.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (sheetBehavior!!.state == BottomSheetBehavior.STATE_COLLAPSED) {
                    rl_gray_bg.visibility = View.GONE
                } else {
                    rl_gray_bg.visibility = View.VISIBLE
                }
            }

            override fun onSlide(
                bottomSheet: View,
                slideOffset: Float
            ) {
            }
        })

        var lst_business_type = ArrayList<String>()
        lst_business_type.add("Select Business Type")
        lst_business_type.add("Sole Trader")
        lst_business_type.add("Company Sole Proprietor")
        lst_business_type.add("Company Private Partnership")
        var adapter_business_type = ArrayAdapter<String>(
            this,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            lst_business_type
        )
        spnrBusinessType.adapter = adapter_business_type

        spnrBusinessType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {

                if (position == 0) {
                    str_business_type = ""
                } else if (position == 1) {
                    str_business_type = "1"
                } else if (position == 2) {
                    str_business_type = "2"
                } else if (position == 3) {
                    str_business_type = "3"
                }
            }

        }

        var retrofit = RetrofitInstance2.getInstance()
        var apiInterface = retrofit!!.create(ApiInterface::class.java)

        val data = apiInterface.getCategory("2", deviceId)

        val data2 = apiInterface.getAllState("2", deviceId)

        data!!.enqueue(object : retrofit2.Callback<GetCategoryRes?> {
            override fun onResponse(
                call: Call<GetCategoryRes?>,
                response: Response<GetCategoryRes?>
            ) {
                if (response.isSuccessful) {
                    if (response.body()!!.status == true) {

                        lst_trade = response.body()!!.data?.category as ArrayList<Category>

                        if (lst_trade.size > 0) {
                            /* setBottomsheetData(lst_trade)*/

                            linear_trade?.removeAllViews()
                            lst_selected_temp.clear()
                            if (lst_selected_trade.size > 0) {
                                lst_selected_temp.addAll(lst_selected_trade)
                            }
                            for (i in lst_trade.indices) {

                                val id: String = lst_trade[i].id
                                val name: String = lst_trade[i].name
                                val viewCheck: View =
                                    LayoutInflater.from(mContext)
                                        .inflate(R.layout.item_trade_checkbox, null)

                                val checkBox = viewCheck.findViewById<CheckBox>(R.id.checkbox)
                                val viewdivider = viewCheck.findViewById<View>(R.id.view_divider)
                                checkBox.setId(id.toInt())
                                checkBox.setText(name)
                                if (lst_selected_trade.size > 0) {
                                    for (j in lst_selected_trade.indices) {
                                        if (lst_selected_trade[j].id.equals(checkBox.id.toString())) {
                                            checkBox.isChecked = true
                                        }
                                    }
                                }
                                checkBox.setOnCheckedChangeListener { compoundButton, b ->
                                    if (b) {
                                        try {
                                            if (lst_trade.get(i).tier != "1") {
                                                dialogUtil.showDialog("You need a formal qualification licence to work")
                                            }// else {
                                            var category =
                                                Category(
                                                    checkBox.id.toString(),
                                                    checkBox.text.toString(),
                                                    lst_trade.get(i).tier
                                                )
                                            lst_selected_temp.add(category)
                                            // }
                                        } catch (e: Exception) {
                                            dialogUtil.showDialog(e.message!!)
                                        }
                                    } else {
                                        for (j in lst_selected_temp.indices) {
                                            if (lst_selected_temp[j].id.equals(checkBox.id.toString())) {
                                                lst_selected_temp.removeAt(j)
                                                break
                                            }
                                        }
                                    }
                                }

                                if (i == 0) {
                                    viewdivider.setVisibility(View.INVISIBLE);
                                } else {
                                    viewdivider.setVisibility(View.VISIBLE);
                                }
                                linear_trade?.addView(viewCheck)
                            }

                        }
                    }
                }
                data2!!.enqueue(object : retrofit2.Callback<ModelClass?> {
                    override fun onResponse(
                        call: Call<ModelClass?>,
                        response: Response<ModelClass?>
                    ) {

                        if (response.isSuccessful) {
                            if (response.body()!!.status == true) {

                                lst_state.addAll(response.body()!!.data?.state!!)
                            }
                        }
                    }

                    override fun onFailure(call: Call<ModelClass?>, t: Throwable) {
                    }


                })
            }

            override fun onFailure(call: Call<GetCategoryRes?>, t: Throwable) {

            }
        })

        edtBusinessAddress!!.setOnClickListener {

            var intent = Intent(this@RegisterActivity2, AddressActivity::class.java)
            startActivityForResult(intent, FIR_PLACE_LOCATION_FROM)
        }

        edtService.setOnClickListener {

            linear_trade?.removeAllViews()
            lst_selected_temp.clear()
            if (lst_selected_trade.size > 0) {
                lst_selected_temp.addAll(lst_selected_trade)
            }
            for (i in lst_trade.indices) {

                val id: String = lst_trade[i].id
                val name: String = lst_trade[i].name
                val tier: String = lst_trade[i].tier
                val viewCheck: View =
                    LayoutInflater.from(mContext).inflate(R.layout.item_trade_checkbox, null)

                val checkBox = viewCheck.findViewById<CheckBox>(R.id.checkbox)
                val viewdivider = viewCheck.findViewById<View>(R.id.view_divider)
                checkBox.setId(id.toInt())
                checkBox.setText(name)
                if (lst_selected_trade.size > 0) {
                    for (j in lst_selected_trade.indices) {
                        if (lst_selected_trade[j].id.equals(checkBox.id.toString())) {
                            checkBox.isChecked = true
                        }
                    }
                }
                checkBox.setOnCheckedChangeListener { compoundButton, b ->
                    if (b) {
                        try {
                            if (lst_trade.get(i).tier != "1") {
                                dialogUtil.showDialog("You need a formal qualification licence to work")
                            }// else {
                            var category =
                                Category(
                                    checkBox.id.toString(),
                                    checkBox.text.toString(),
                                    lst_trade.get(i).tier
                                )
                            lst_selected_temp.add(category)
                            // }
                        } catch (e: Exception) {
                            dialogUtil.showDialog(e.message!!)
                        }
                    } else {
                        for (j in lst_selected_temp.indices) {
                            if (lst_selected_temp[j].id.equals(checkBox.id.toString())) {
                                lst_selected_temp.removeAt(j)
                                break
                            }
                        }
                    }
                }

                if (i == 0) {
                    viewdivider.setVisibility(View.INVISIBLE);
                } else {
                    viewdivider.setVisibility(View.VISIBLE);
                }
                linear_trade?.addView(viewCheck)
            }

            if (sheetBehavior!!.state == BottomSheetBehavior.STATE_COLLAPSED) {
                sheetBehavior!!.state = BottomSheetBehavior.STATE_EXPANDED
            }


        }

        btnregister.setOnClickListener {

            progressDialogUtil.showProgressDialog("Loading...")

            var address = AddressX(
                edtBusinessAddress!!.text.toString(),
                city,
                latitude,
                longitude,
                postalCode,
                state_id,
                suburb
            )

            var lst_categories = ArrayList<Int>()

            for (i in lst_selected_trade.indices) {
                lst_categories.add(Integer.parseInt(lst_selected_trade[i].id))
            }

            var abn = ""
            abn = edtABN.text!!.trim().toString()
            var regsterReq = RegisterRequest(
                abn,
                address,
                edtBusinessName.text!!.trim().toString(),
                str_business_type,
                lst_categories,
                str_mobile,
                str_dob,
                str_email,
                str_firstname,
                str_lastname,
                str_password
            )
            var data = apiInterface.register("2", deviceId, regsterReq)

            data!!.enqueue(object : retrofit2.Callback<RegisterResponse?> {
                override fun onResponse(
                    call: Call<RegisterResponse?>,
                    response: Response<RegisterResponse?>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()!!.status == true) {
                            progressDialogUtil.hideProgressDialog()

                            var loginResponse: LoginResponseModel? = null
                            var address = Address()
                            address.address = response.body()!!.data.address?.address
                            address.id = response.body()!!.data.address?.id
                            address.lat = response.body()!!.data.address?.lat
                            address.lng = response.body()!!.data.address?.lng
                            address.postcode = response.body()!!.data.address?.postcode
                            address.suburb = response.body()!!.data.address?.suburb
                            address.stateId = response.body()!!.data.address?.stateId

                            var company = Company()

                            company.abn = response.body()!!.data.company?.abn
                            company.about = response.body()!!.data.company?.about
                            company.id = response.body()!!.data.company?.id
                            company.name = response.body()!!.data.company?.name
                            company.picture = response.body()!!.data.company?.picture

                            var user = User()

                            user.id = response.body()!!.data.user?.id
                            user.contactNo = response.body()!!.data.user?.contactNo
                            user.email = response.body()!!.data.user?.email
                            user.firstName = response.body()!!.data.user?.firstName
                            user.lastName = response.body()!!.data.user?.lastName

                            loginResponse?.data?.key = response.body()!!.data?.key
                            loginResponse?.data?.address = address
                            loginResponse?.data?.company = company
                            loginResponse?.data?.user = user

                            val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
                            editor.putString("loginData", Gson().toJson(loginResponse))
                            editor.putString("LOGIN_KEY", response.body()!!.data.key)
                            editor.apply()
                            editor.commit()

                            val intent = Intent(this@RegisterActivity2, HomeActivity::class.java)
                            /*intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK || Intent.FLAG_ACTIVITY_CLEAR_TOP || Intent.FLAG_ACTIVITY_NEW_TASK)*/
                            /*intent.putExtra(Bundle())*/
                            startActivity(intent)
                            finish()

                            Log.d("Testinggg", "Successss " + response.body()!!.message)
                        } else {
                            Log.d("Testinggg", "Else status " + response.body()!!.message)
                            progressDialogUtil.hideProgressDialog()
                            dialogUtil.showDialog(response.body()!!.message)
                        }
                    } else {
                        Log.d("Testinggg", "Else successfull " + response.body()!!.message)
                        progressDialogUtil.hideProgressDialog()
                        dialogUtil.showDialog(response.body()!!.message)

                    }
                }

                override fun onFailure(call: Call<RegisterResponse?>, t: Throwable) {
                    Log.d("Testinggg", "Failll : " + t.localizedMessage)
                    progressDialogUtil.hideProgressDialog()
                    dialogUtil.showDialog(t.localizedMessage)
                }


            })
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun setTradeSelectedTextview() {
        flexService!!.removeAllViews()
        for (i in lst_selected_trade.indices) {

            val name: String = lst_selected_trade[i].name
            val viewText: View =
                LayoutInflater.from(mContext).inflate(R.layout.item_trade_textview, null)

            val textView = viewText.findViewById<TextView>(R.id.txt_trade)

            textView.setText(name)
            flexService!!.addView(viewText, i)
        }
    }

    private fun retrofitInit() {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(object : Interceptor {
                @Throws(IOException::class)
                override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
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
            .baseUrl("https://maps.googleapis.com/maps/api/geocode/")
            .client(httpClient)
            .build()
        apiService = retrofit.create(ApiInterface::class.java)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    if (data!!.hasExtra("placeId") && data.hasExtra("addressDiscription")) {
                        placeId = data!!.getStringExtra("placeId").toString()
                        addressDiscription = data!!.getStringExtra("addressDiscription").toString()

                        var latitudeLocal = ""
                        var longitudeLocal = ""
                        var postalCodeLocal = ""
                        var suburbLocal = ""
                        var stateLocal = ""
                        var state_idLocal = ""
                        var cityLocal = ""

                        apiService.getLatlong(placeId)!!
                            .enqueue(object : Callback<GetLatLongById?> {
                                override fun onResponse(
                                    call: Call<GetLatLongById?>,
                                    response: Response<GetLatLongById?>
                                ) {
                                    if (response.isSuccessful) {
                                        when (requestCode) {
                                            FIR_PLACE_LOCATION_FROM ->

                                                if (response.body()!!.status.equals("OK")) {
                                                    latitudeLocal =
                                                        response.body()!!.results?.get(0)!!.geometry?.location?.lat.toString()
                                                    longitudeLocal =
                                                        response.body()!!.results?.get(0)!!.geometry?.location?.lng.toString()


                                                    var address_componant =
                                                        response.body()!!.results?.get(0)!!.addressComponents

                                                    for (i in address_componant.indices) {
                                                        if (address_componant[i].types[0].equals("postal_code")) {
                                                            postalCodeLocal =
                                                                address_componant[i].longName!!
                                                            break
                                                        }
                                                    }

                                                    for (i in address_componant.indices) {
                                                        if (address_componant[i].types[0].equals("locality")) {
                                                            suburbLocal =
                                                                address_componant[i].longName!!
                                                            break
                                                        }
                                                    }

                                                    for (i in address_componant.indices) {
                                                        if (address_componant[i].types[0].equals("administrative_area_level_1")) {
                                                            stateLocal =
                                                                address_componant[i].longName!!
                                                            break
                                                        }
                                                    }

                                                    for (i in lst_state.indices) {
                                                        if (lst_state[i].name!!.toLowerCase()
                                                                .equals(stateLocal.toLowerCase())
                                                        ) {
                                                            state_idLocal =
                                                                lst_state[i].id.toString()

                                                            break
                                                        }
                                                    }
                                                    val geocoder = Geocoder(
                                                        applicationContext,
                                                        Locale.getDefault()
                                                    )

                                                    try {
                                                        var addresses = geocoder.getFromLocation(
                                                            latitudeLocal!!.toDouble(),
                                                            longitudeLocal!!.toDouble(),
                                                            1
                                                        );
                                                        if (addresses.get(0)
                                                                .getLocality() != null
                                                        ) {
                                                            cityLocal =
                                                                addresses.get(0).getLocality();
                                                        }
                                                    } catch (e: Exception) {
                                                        e.printStackTrace()
                                                    }

                                                    if (latitudeLocal.isNullOrEmpty() || longitudeLocal.isNullOrEmpty() || postalCodeLocal.isNullOrEmpty() || suburbLocal.isNullOrEmpty() || state_idLocal.isNullOrEmpty() || cityLocal.isNullOrEmpty()) {
                                                        dialogUtil.showDialog("Please add valid address")
                                                    } else {
                                                        latitude = latitudeLocal
                                                        longitude = longitudeLocal
                                                        postalCode = postalCodeLocal
                                                        suburb = suburbLocal
                                                        state = stateLocal
                                                        state_id = state_idLocal
                                                        city = cityLocal

                                                        edtBusinessAddress?.setText(
                                                            addressDiscription
                                                        )
                                                    }

                                                }
                                        }
                                    }
                                }

                                override fun onFailure(call: Call<GetLatLongById?>, t: Throwable) {
                                    Log.d("Testinggg", "Failll : " + t.localizedMessage)
                                }


                            })
                    }
                }
                Activity.RESULT_CANCELED -> {
                }
                else -> {
                }
            }

        }
    }
}

