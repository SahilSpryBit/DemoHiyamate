package com.example.demohiyamate.model.google

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MatchedSubstring (

    @SerializedName("length" ) var length : Int? = null,
    @SerializedName("offset" ) var offset : Int? = null

)