package com.example.demohiyamate.model.google.latlon

import com.google.gson.annotations.SerializedName

data class Bounds (

    @SerializedName("northeast" ) var northeast : Northeast? = Northeast(),
    @SerializedName("southwest" ) var southwest : Southwest? = Southwest()

)