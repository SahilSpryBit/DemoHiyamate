package com.example.demohiyamate.model.google.latlon

import com.google.gson.annotations.SerializedName

data class Geometry (

    @SerializedName("bounds"        ) var bounds       : Bounds?   = Bounds(),
    @SerializedName("location"      ) var location     : Location? = Location(),
    @SerializedName("location_type" ) var locationType : String?   = null,
    @SerializedName("viewport"      ) var viewport     : Viewport? = Viewport()

)