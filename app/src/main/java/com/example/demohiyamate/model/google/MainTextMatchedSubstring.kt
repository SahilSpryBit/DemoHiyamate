package com.example.demohiyamate.model.google

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MainTextMatchedSubstring (

    @SerializedName("length" ) var length : Int? = null,
    @SerializedName("offset" ) var offset : Int? = null

)