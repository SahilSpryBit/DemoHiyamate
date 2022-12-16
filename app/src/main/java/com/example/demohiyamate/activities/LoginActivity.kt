package com.example.demohiyamate.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.demohiyamate.R
import com.example.demohiyamate.model.login.LoginRequest
import com.example.demohiyamate.model.login.LoginResponseModel
import com.example.demohiyamate.network.ApiInterface
import com.example.demohiyamate.network.RetrofitInstance2
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.hiyamate.utils.ProgressDialogUtil
import retrofit2.Call
import retrofit2.Response
import java.util.regex.Pattern

class LoginActivity : BaseActivity() {

    var sharedPreference : SharedPreferences? = null
    var etemail : TextInputEditText? = null
    var etpass : TextInputEditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white))
        }

        setContentView(R.layout.activity_login)

        etemail = findViewById<TextInputEditText>(R.id.etemail)
        var textemail = findViewById<TextInputLayout>(R.id.textemail)
        etpass = findViewById<TextInputEditText>(R.id.etpass)
        var textpass = findViewById<TextInputLayout>(R.id.textpass)
        val btnlogin = findViewById<Button>(R.id.btnLogin)
        val txtForgotPassword = findViewById<TextView>(R.id.txtForgotPassword)
        val txtregister = findViewById<TextView>(R.id.txtregister)

        sharedPreference =  getSharedPreferences("PREFERENCE_NAME",Context.MODE_PRIVATE)



        btnlogin.setOnClickListener{

            textemail.error = "";
            textpass.error = ""

            if(etemail!!.text.toString().isNullOrEmpty()){
                textemail.error = "Please enter email"
                etemail!!.requestFocus()
            }else if(!Patterns.EMAIL_ADDRESS.matcher(etemail!!.text).matches() || !isValidEmailId(etemail!!.text!!.trim().toString())){
                textemail.error = "Please enter valid email"
                etemail!!.requestFocus()
            }else if (etpass!!.text.isNullOrEmpty()) {
                textpass.error = "Please enter password"
                etpass!!.requestFocus()

            } else if (etpass!!.text!!.length < 6) {
                textpass.error = "Please enter at least 6 characters"
                etpass!!.requestFocus()
            }else {

                progressDialogUtil.showProgressDialog("Loading...")

                loginApi()

            }
        }

        txtForgotPassword.setOnClickListener {

            val intent = Intent(this@LoginActivity, ForgetPasswordActivity::class.java)
            startActivity(intent)
        }

        txtregister.setOnClickListener {

            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }

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

    private fun loginApi(){

        var deviceId = sharedPreference!!.getString("DEVICE_ID", "")

        var retrofit = RetrofitInstance2.getInstance()
        var apiInterface = retrofit!!.create(ApiInterface::class.java)
        val login = LoginRequest(etemail!!.text.toString().trim(), etpass!!.text.toString().trim());
        var data = apiInterface.login("2", deviceId, login)

        data!!.enqueue(object : retrofit2.Callback<LoginResponseModel?> {
            override fun onResponse(
                call: Call<LoginResponseModel?>,
                response: Response<LoginResponseModel?>
            ) {
                if (response.isSuccessful) {
                    if (response.body()!!.status == true) {

                        Log.d("Testinggg", "" + response.body()!!.message)
                        Log.d(
                            "Testinggg",
                            "Name : " + response.body()!!.data?.user?.firstName + " " + response.body()!!.data?.user?.lastName
                        )
                        Log.d(
                            "Testinggg",
                            "Address : " + response.body()!!.data?.address?.address
                        )
                        Log.d(
                            "Testinggg",
                            "Company Name : " + response.body()!!.data?.company?.name
                        )
                        val editor: SharedPreferences.Editor = sharedPreference!!.edit()
                        editor.putString(
                            "LOGIN_KEY",
                            response.body()!!.data?.key.toString()
                        )
                        editor.apply()
                        editor.commit()
                        Toast.makeText(this@LoginActivity, "Login Successful", Toast.LENGTH_LONG)
                        progressDialogUtil.hideProgressDialog()
                        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                        startActivity(intent)
                        finish()
                        Toast.makeText(this@LoginActivity, "SUccessss", Toast.LENGTH_LONG)
                    } else {
                        progressDialogUtil.hideProgressDialog()
                        dialogUtil.showDialog(response.body()!!.message)
                        etemail!!.requestFocus()
                        Log.d("Testinggg", etemail!!.text.toString() + "  Elseee : " + response.body()!!.message
                        )
                    }
                }
            }

            override fun onFailure(call: Call<LoginResponseModel?>, t: Throwable) {
                Log.d("Testinggg", "Failllll : " + t.localizedMessage)
                dialogUtil.showDialog(R.string.something_wrong)
                progressDialogUtil.hideProgressDialog()
                Toast.makeText(this@LoginActivity, "Failllll : " + t.localizedMessage, Toast.LENGTH_LONG
                )
            }

        })
    }
}