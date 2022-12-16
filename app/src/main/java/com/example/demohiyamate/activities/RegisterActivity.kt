package com.example.demohiyamate.activities

import android.R.attr.text
import android.R.id.text2
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.BackgroundColorSpan
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.demohiyamate.R
import com.example.demohiyamate.model.register.EmailCheckRequest
import com.example.demohiyamate.model.register.EmailResponse
import com.example.demohiyamate.network.ApiInterface
import com.example.demohiyamate.network.RetrofitInstance2
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Response
import java.util.*
import java.util.regex.Pattern


class RegisterActivity : BaseActivity() {

    var sharedPreference : SharedPreferences? = null

    var etemailregister : TextInputEditText? = null
    var etfirstname : TextInputEditText? = null
    var etlastname : TextInputEditText? = null
    var etmobile : TextInputEditText? = null
    var etpassregister : TextInputEditText? = null
    var etdateofbirth : TextInputEditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white))
        }

        setContentView(R.layout.activity_register)

        sharedPreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)

        var btnnext = findViewById<Button>(R.id.btnregister)
        etemailregister = findViewById<TextInputEditText>(R.id.etemailregister)
        etfirstname = findViewById<TextInputEditText>(R.id.etfirstname)
        etlastname = findViewById<TextInputEditText>(R.id.etlastname)
        etmobile = findViewById<TextInputEditText>(R.id.etmobile)
        etpassregister = findViewById<TextInputEditText>(R.id.etpassregister)
        etdateofbirth = findViewById<TextInputEditText>(R.id.etdateofbirth)
        var txtpassregister = findViewById<TextInputLayout>(R.id.txtpassregister)
        var txtemailregister = findViewById<TextInputLayout>(R.id.txtemailregister)
        var txtmobile = findViewById<TextInputLayout>(R.id.txtmobile)
        var txtdateofbirth = findViewById<TextInputLayout>(R.id.txtdateofbirth)
        var txtlastname = findViewById<TextInputLayout>(R.id.txtlastname)
        var txtfrstname = findViewById<TextInputLayout>(R.id.txtfrstname)
        var imageViewback = findViewById<ImageView>(R.id.imageViewback)
        var textview = findViewById<TextView>(R.id.txttermsconditions)

        imageViewback.setOnClickListener {
            onBackPressed()
        }

        etdateofbirth!!.setOnClickListener {

            var mYear: Int
            var mMonth: Int
            var mDay: Int
            if (etdateofbirth!!.text.isNullOrEmpty()) {
            val c = Calendar.getInstance();
            mYear = (c.get(Calendar.YEAR));
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
            showDatePickerDialog(etdateofbirth, mDay, mMonth, mYear)
            } else {
                var date = etdateofbirth!!.text.toString()
                mDay = Integer.parseInt(date.split("/")[0])
                mMonth = (Integer.parseInt(date.split("/")[1]) - 1)
                mYear = Integer.parseInt(date.split("/")[2])
                showDatePickerDialog(etdateofbirth, mDay, mMonth, mYear)
            }
        }

        btnnext.setOnClickListener {

            txtfrstname.error = ""
            txtlastname.error = ""
            txtdateofbirth.error = ""
            txtmobile.error = ""
            txtemailregister.error = ""
            txtpassregister.error = ""

            if (etfirstname!!.text!!.trim().isEmpty()) {
                txtfrstname.error ="Please enter first name"
                etfirstname!!.requestFocus()
            } else if (etlastname!!.text!!.trim().isEmpty()) {
                txtlastname.error ="Please enter first name"
                etlastname!!.requestFocus()
            } else if (etdateofbirth!!.text!!.trim().isEmpty()) {
                txtdateofbirth.error ="Please enter date of birth"
            } else if (etmobile!!.text!!.trim().isEmpty()) {
                txtmobile.error = "Please enter mobile no"
                etmobile!!.requestFocus()
            } else if (etmobile!!.text!!.trim().length < 10) {
                txtmobile.error = "Please enter valid mobile no"
                etmobile!!.requestFocus()
            } else if (etemailregister!!.text!!.trim().isEmpty()) {
                txtemailregister.error = "Please enter email"
                etemailregister!!.requestFocus()
            } else if (!Patterns.EMAIL_ADDRESS.matcher(etemailregister!!.text!!.trim().toString()).matches() || !isValidEmailId(etemailregister!!.text!!.trim().toString())) {
                txtemailregister.error = "Please enter valid email"
                etemailregister!!.requestFocus()
            }else if (etpassregister!!.text.isNullOrEmpty()) {
                txtpassregister.error ="Please enter password"
                etpassregister!!.requestFocus()
            } else if (etpassregister!!.text!!.length < 6) {
                txtpassregister.error ="Please enter at least 6 characters"
                etpassregister!!.requestFocus()
            } else {

                progressDialogUtil.showProgressDialog("Loading...")

                checkEmailApi()
            }

        }

      /*  val tearms = "terms and conditions"
        val privacy ="privacy policy"
        val str_privacy = " and " +"privacy policy "
        val str_tearms = " " +"terms and conditions"
        val yourString = "By continuing, you agree with our "+ str_tearms
        val newstring = "By continuing, you agree with our" + str_tearms+ str_privacy
        val spannableStringBuilder = SpannableStringBuilder(yourString).append(str_privacy)
        spannableStringBuilder.setSpan(
            object : ClickableSpan() {
                override fun onClick(widget: View) {

                    Log.d("Testinggg", "terms and conditions")

                }

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.isUnderlineText = false
                    ds.isFakeBoldText = false
                    ds.color = resources.getColor(R.color.colorBlue)
                }
            },
            (yourString.length - 1) - (tearms.length - 1),
            yourString.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableStringBuilder.setSpan(
            object : ClickableSpan() {
                override fun onClick(widget: View) {
                    Log.d("Testinggg", "privacy policy")
                }

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.isUnderlineText = false
                    ds.isFakeBoldText = false
                    ds.color = resources.getColor(R.color.colorBlue)

                }
            },
            (newstring.length - 1) - (privacy.length - 1),
            newstring.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        textview.movementMethod = LinkMovementMethod.getInstance()
        textview.setText(spannableStringBuilder, TextView.BufferType.SPANNABLE)*/
    }

     fun showDatePickerDialog(
        editText: TextInputEditText?,
        mDay: Int,
        mMonth: Int,
        mYear: Int
    ) {
        var datePickerDialog = DatePickerDialog(
            this@RegisterActivity,

            object : DatePickerDialog.OnDateSetListener {

                override fun onDateSet(
                    p0: DatePicker?,
                    year: Int,
                    monthOfYear: Int,
                    dayOfMonth: Int
                ) {
                    var day = ""
                    var month = ""
                    var sYear = ""
                    if (dayOfMonth < 10) {
                        day = "0" + dayOfMonth.toString()
                    } else {
                        day = dayOfMonth.toString()
                    }
                    if ((monthOfYear + 1) < 10) {
                        month = "0" + (monthOfYear + 1).toString()
                    } else {
                        month = (monthOfYear + 1).toString()
                    }
                    sYear = year.toString()
                    var strDate = day + "/" + month + "/" + sYear
                    editText!!.setText(strDate)
                }
            }, mYear, mMonth, mDay
        )
        var calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, (calendar.get(Calendar.DAY_OF_MONTH) - 1))
        calendar.set(Calendar.MONTH, (calendar.get(Calendar.MONTH)))
        calendar.set(Calendar.YEAR, (calendar.get(Calendar.YEAR) - 13))
        datePickerDialog.getDatePicker().setMaxDate(calendar.timeInMillis);

        datePickerDialog.show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    fun isValidEmailId(email: String?): Boolean {
        return Pattern.compile(
            "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
        ).matcher(email).matches()
    }

    private fun checkEmailApi(){

        var retrofit = RetrofitInstance2.getInstance()
        var apiInterface = retrofit!!.create(ApiInterface::class.java)
        var deviceId = sharedPreference!!.getString("DEVICE_ID", "")

        val emailCheck =
            EmailCheckRequest(etemailregister!!.text.toString().trim())

        var data = apiInterface.checkEmail("2", deviceId, emailCheck)

        data!!.enqueue(object : retrofit2.Callback<EmailResponse?> {
            override fun onResponse(
                call: Call<EmailResponse?>,
                response: Response<EmailResponse?>
            ) {
                if (response.isSuccessful) {

                    if (response.body()!!.status == true) {

                        var str_dob = etdateofbirth!!.text.toString()

                        progressDialogUtil.hideProgressDialog()
                        val intent = Intent(this@RegisterActivity, RegisterActivity2::class.java)
                        intent.putExtra("first_name", etfirstname!!.text!!.trim().toString())
                        intent.putExtra("last_name",etlastname!!.text!!.trim().toString())
                        intent.putExtra("mobile", etmobile!!.text!!.trim().toString())
                        intent.putExtra("email", etemailregister!!.text!!.trim().toString())
                        intent.putExtra("password", etpassregister!!.text.toString())
                        intent.putExtra("dob", str_dob)
                        startActivity(intent)

                        Log.d("Testinggg", "Sucesss : " + response.body()!!.message)
                    }else{
                        progressDialogUtil.hideProgressDialog()
                        dialogUtil.showDialog(response.body()!!.message)
                    }
                }
            }

            override fun onFailure(call: Call<EmailResponse?>, t: Throwable) {
                progressDialogUtil.hideProgressDialog()
                dialogUtil.showDialog(t.localizedMessage)
            }

        })
    }
}