package com.hiyamate.utils

import android.app.Dialog
import android.content.Context
import android.graphics.PorterDuff
import android.view.Window
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.demohiyamate.R


class ProgressDialogUtil(val context: Context) {
    private var progressDialog: Dialog? = null

    fun showProgressDialog(message: String) {
        try {
            if (progressDialog == null) {
                progressDialog = getProgressDialog(context, message)
            }
            if (!progressDialog!!.isShowing) {
                progressDialog!!.show()
            }

        } catch (e: Exception) {
            e.message
        }

    }

    fun hideProgressDialog() {
        try {
            if (progressDialog != null && progressDialog!!.isShowing) {
                progressDialog!!.dismiss()
            }
        }catch (e: Exception){
            e.message
        }
    }


    private fun getProgressDialog(context: Context, message: String): Dialog {
        progressDialog = Dialog(context)
        progressDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        progressDialog!!.setContentView(R.layout.dialog_progress)
        progressDialog!!.setCanceledOnTouchOutside(false)
        progressDialog!!.setCancelable(false)
        progressDialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        val progressBar = progressDialog!!.findViewById<ProgressBar>(R.id.dialogProgressBar)
        val textView = progressDialog!!.findViewById<TextView>(R.id.loading_msg)
        textView.text = message
        progressBar.indeterminateDrawable.setColorFilter(
            ContextCompat.getColor(context, R.color.colorPrimary),
            PorterDuff.Mode.MULTIPLY
        )
        return progressDialog!!


    }
}