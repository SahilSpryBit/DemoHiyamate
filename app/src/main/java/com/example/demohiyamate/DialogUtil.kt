package com.hiyamate.utils

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import com.example.demohiyamate.R
import com.example.demohiyamate.activities.LoginActivity


class DialogUtil(private val context: Context) {

    private val builder: AlertDialog.Builder = AlertDialog.Builder(context)

    init {
        builder.setCancelable(true)
        builder.setPositiveButton("Ok") { dialog, _ -> dialog.dismiss() }
    }

    fun showDialog(strMsg: Int) {
        builder.setMessage(context.resources.getString(strMsg))

        val alert = builder.create()
        alert.setCanceledOnTouchOutside(true)
        alert.show()
    }


    fun showDialog(errorMsg: String?) {
        var msg: String? = errorMsg

        if (msg.isNullOrEmpty())
            msg = context.resources.getString(R.string.something_wrong)

        builder.setMessage(msg)

        val alert = builder.create()
        alert.setCanceledOnTouchOutside(true)
        alert.show()

    }



    fun showAuthorizationDialog(errorMsg: String?,isRedirectToLogin: Boolean) {
        var msg: String? = errorMsg

        if (msg.isNullOrEmpty())
            msg = context.resources.getString(R.string.something_wrong)

        builder.setMessage(msg)
        builder.setPositiveButton("ok") {

                dialog: DialogInterface?, _: Int ->
            if(isRedirectToLogin){
                context.startActivity(
                    Intent(context, LoginActivity::class.java).addFlags(
                        Intent.FLAG_ACTIVITY_CLEAR_TASK
                    ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                dialog?.dismiss()
            }else {
                dialog?.dismiss()
            }
        }

        val alert = builder.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()

    }

}
