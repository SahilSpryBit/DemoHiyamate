package com.example.demohiyamate.model

import com.google.gson.annotations.SerializedName

class ModelClass {

    @SerializedName("status"      ) var status     : Boolean? = null
    @SerializedName("data"        ) var data       : Data?    = Data()
    @SerializedName("error"       ) var error      : String?  = null
    @SerializedName("status_code" ) var statusCode : Int?     = null
    @SerializedName("message"     ) var message    : String?  = null

}