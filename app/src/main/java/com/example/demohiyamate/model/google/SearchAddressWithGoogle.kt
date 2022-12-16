package com.example.demohiyamate.model.google

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SearchAddressWithGoogle (

    @SerializedName("predictions" ) var predictions : ArrayList<Prediction> = arrayListOf(),
    @SerializedName("status"      ) var status      : String?                = null

)