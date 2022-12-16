package com.example.demohiyamate.model.login

import com.example.demohiyamate.model.Data
import com.google.gson.annotations.SerializedName

class LoginResponseModel {

    var status: Boolean? = null

    var message: String? = null

    var status_code: Int? = null

    var error: String? = null

    @SerializedName("data") var data : Data?    = Data()
}