package com.example.demohiyamate.model.google.latlon

import com.google.gson.annotations.SerializedName


data class GetLatLongById (

    @SerializedName("results" ) var results : ArrayList<Results> = arrayListOf(),
    @SerializedName("status"  ) var status  : String?            = null

)