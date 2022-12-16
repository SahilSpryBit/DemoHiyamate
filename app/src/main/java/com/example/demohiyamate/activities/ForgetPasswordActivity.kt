package com.example.demohiyamate.activities

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import com.example.demohiyamate.MyApplication
import com.example.demohiyamate.R
import com.example.demohiyamate.model.ForgotPasswordReq
import com.example.demohiyamate.model.ModelClass
import com.example.demohiyamate.model.ResetPasswordReq
import com.example.demohiyamate.network.ApiInterface
import com.example.demohiyamate.network.RetrofitInstance2
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Response
import java.util.regex.Pattern

class ForgetPasswordActivity : BaseActivity() {
    var sheetBehavior: BottomSheetBehavior<*>? = null
    lateinit var bottom_sheet: CoordinatorLayout
    lateinit var rl_gray_bg: FrameLayout

    var etemailreset : TextInputEditText? = null
    var edtResetEmail : TextInputEditText? = null
    var edtTempPassword : TextInputEditText? = null
    var edtPassword : TextInputEditText? = null
    var edtRetypePassword : TextInputEditText? = null

    var sharedPreference : SharedPreferences? = null
    var deviceId : String? = ""
    lateinit var apiInterface : ApiInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white))
        }

        setContentView(R.layout.activity_forget_password)

        var imageViewback = findViewById<ImageView>(R.id.imageViewback)
        etemailreset = findViewById<TextInputEditText>(R.id.etemailreset)
        edtResetEmail = findViewById<TextInputEditText>(R.id.edtResetEmail)
        edtTempPassword = findViewById<TextInputEditText>(R.id.edtTempPassword)
        edtPassword = findViewById<TextInputEditText>(R.id.edtPassword)
        edtRetypePassword = findViewById<TextInputEditText>(R.id.edtRetypePassword)
        var txtemailreset = findViewById<TextInputLayout>(R.id.txtemailreset)
        var txtResetInpEmail = findViewById<TextInputLayout>(R.id.txtResetInpEmail)
        var txtTempPassword = findViewById<TextInputLayout>(R.id.txtTempPassword)
        var txtPassword = findViewById<TextInputLayout>(R.id.txtPassword)
        var txtRetypePassword = findViewById<TextInputLayout>(R.id.txtRetypePassword)
        var btnResetpass = findViewById<Button>(R.id.btnResetpass)
        var btnResetPassword2 = findViewById<Button>(R.id.btnResetPassword2)

        bottom_sheet = findViewById<CoordinatorLayout>(R.id.bottom_sheet)
        sheetBehavior = BottomSheetBehavior.from<CoordinatorLayout>(bottom_sheet)
        bottom_sheet.bringToFront()
        rl_gray_bg = findViewById<FrameLayout>(R.id.rl_bg)

        sharedPreference =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)

        deviceId = sharedPreference!!.getString("DEVICE_ID", "")

        var retrofit = RetrofitInstance2.getInstance()
        apiInterface = retrofit!!.create(ApiInterface::class.java)

        btnResetpass.setOnClickListener {

            progressDialogUtil.showProgressDialog("Loading...")

            txtemailreset.error = ""

            if(etemailreset!!.text.isNullOrEmpty()){
                txtemailreset.error = "Please enter email"
                etemailreset!!.requestFocus()
            }else if(!Patterns.EMAIL_ADDRESS.matcher(etemailreset!!.text).matches() || !isValidEmailId(etemailreset!!.text!!.trim().toString())){
                txtemailreset.error = "Please enter valid email"
                etemailreset!!.requestFocus()
            }else{
                 Log.d("Testinggg", "Succeessss")

                ForgotPasswordApi()

            }
        }

        imageViewback.setOnClickListener {
            onBackPressed()
        }

        btnResetPassword2.setOnClickListener {

            progressDialogUtil.showProgressDialog("Loading...")

            txtTempPassword.error = ""
            txtPassword.error = ""
            txtRetypePassword.error = ""

            if (edtTempPassword!!.text.isNullOrEmpty()) {
                txtTempPassword.error = "Please enter OTP"
                edtTempPassword!!.requestFocus()

            }
         else if (edtPassword!!.text.isNullOrEmpty()) {
                txtPassword.error = "Please enter new password"
                edtPassword!!.requestFocus()
            } else if (edtPassword!!.text!!.length < 6) {
                txtPassword.error = "Please enter at least 6 characters"
                edtPassword!!.requestFocus()
            } else if (edtRetypePassword!!.text.isNullOrEmpty()) {
                txtRetypePassword.error = "Please retype password"
                edtRetypePassword!!.requestFocus()
            } else if (!edtRetypePassword!!.text!!.toString()
                    .equals(edtPassword!!.text!!.toString())
            ) {
                txtRetypePassword.error = "New password and retype password must be same"
                edtRetypePassword!!.requestFocus()
            } else {
                ResetPasswordApi()
            }


        }
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

    private fun ForgotPasswordApi(){

        var forgotPasswordReq = ForgotPasswordReq(
            etemailreset!!.text.toString().trim())

        val data = apiInterface.forgotPassword("2", deviceId, forgotPasswordReq)

        data!!.enqueue(object : retrofit2.Callback<ModelClass?>{
            override fun onResponse(
                call: Call<ModelClass?>,
                response: Response<ModelClass?>
            ) {
                if(response.isSuccessful){
                    if(response.body()!!.status == true){

                        progressDialogUtil.hideProgressDialog()

                        edtResetEmail!!.setText(etemailreset!!.text.toString().trim())
                        edtTempPassword!!.setText("")
                        edtPassword!!.setText("")
                        edtRetypePassword!!.setText("")

                        if (sheetBehavior!!.state == BottomSheetBehavior.STATE_COLLAPSED) {
                            sheetBehavior!!.state = BottomSheetBehavior.STATE_EXPANDED
                        }

                    }else{
                        progressDialogUtil.hideProgressDialog()
                        dialogUtil.showDialog(response.body()!!.message)
                    }
                }else{
                    progressDialogUtil.hideProgressDialog()
                    dialogUtil.showDialog(response.body()!!.message)
                }
            }

            override fun onFailure(call: Call<ModelClass?>, t: Throwable) {
                progressDialogUtil.hideProgressDialog()
                dialogUtil.showDialog(t.localizedMessage)
            }


        })
    }

    private fun ResetPasswordApi(){

        var resetPasswordReq = ResetPasswordReq(
            edtResetEmail!!.text.toString().trim(),
            edtTempPassword!!.text.toString().trim(),
            edtPassword!!.text.toString().trim()
        )

        val data2 = apiInterface.resetPassword("2", deviceId, resetPasswordReq)

        data2!!.enqueue(object : retrofit2.Callback<ModelClass?>{
            override fun onResponse(call: Call<ModelClass?>, response: Response<ModelClass?>) {
                if(response.isSuccessful){
                    if(response.body()!!.status == true){
                        progressDialogUtil.hideProgressDialog()
                        dialogUtil.showAuthorizationDialog(response.body()!!.message , true)

                    }else{
                        progressDialogUtil.hideProgressDialog()
                        dialogUtil.showDialog(response.body()!!.message)
                    }
                }else{
                    progressDialogUtil.hideProgressDialog()
                    dialogUtil.showDialog(response.body()!!.message)
                }
            }

            override fun onFailure(call: Call<ModelClass?>, t: Throwable) {
                progressDialogUtil.hideProgressDialog()
                dialogUtil.showDialog(t.message)
            }

        })
    }
}