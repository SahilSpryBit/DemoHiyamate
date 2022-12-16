package com.example.demohiyamate.model.google

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Term (

    @SerializedName("offset" ) var offset : Int?    = null,
    @SerializedName("value"  ) var value  : String? = null

)