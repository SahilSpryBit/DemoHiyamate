package com.example.demohiyamate.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.demohiyamate.R

class SettingActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        var iv_back = findViewById<ImageView>(R.id.iv_back)
        var logout = findViewById<TextView>(R.id.logout)

        iv_back.setOnClickListener {
            onBackPressed()
        }

        val sharedPreference =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)

        logout.setOnClickListener {

            val builder = AlertDialog.Builder(this)
            builder.setMessage("Are you sure you want to logout?")
            builder.setCancelable(true)
            builder.setPositiveButton("LOGOUT") {
                    dialog, which ->
                sharedPreference.edit().clear().apply()
                dialogUtil.showDialog("Login session expired, please re-login")
                val intent = Intent(this@SettingActivity, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }

            builder.setNegativeButton("CANCEL") {
                    dialog, which -> dialog.cancel()
            }
            val alertDialog = builder.create()
            alertDialog.show()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}