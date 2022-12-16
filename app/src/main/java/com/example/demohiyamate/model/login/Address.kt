package com.example.demohiyamate.model.login

import com.google.gson.annotations.SerializedName

data class Address (

    @SerializedName("id"       ) var id       : String? = null,
    @SerializedName("address"  ) var address  : String? = null,
    @SerializedName("suburb"   ) var suburb   : String? = null,
    @SerializedName("city"     ) var city     : String? = null,
    @SerializedName("state_id" ) var stateId  : String? = null,
    @SerializedName("postcode" ) var postcode : String? = null,
    @SerializedName("lat"      ) var lat      : String? = null,
    @SerializedName("lng"      ) var lng      : String? = null

)