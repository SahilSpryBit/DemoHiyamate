package com.example.demohiyamate.activities

import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.hiyamate.utils.DialogUtil
import com.hiyamate.utils.ProgressDialogUtil

open class BaseActivity : AppCompatActivity() {

    lateinit var progressDialogUtil: ProgressDialogUtil
    lateinit var dialogUtil: DialogUtil

    var ID_Status = ""
    var ID_Color = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        progressDialogUtil = ProgressDialogUtil(this)
        dialogUtil = DialogUtil(this)

    }

    fun hidestatusBar() {

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                window.setDecorFitsSystemWindows(false)
                val controller = window.insetsController
                if (controller != null) {
                    controller.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                    controller.systemBarsBehavior =
                        WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                }

            } else {
                window.setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
                )
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}