package com.example.demohiyamate.model

import com.google.gson.annotations.SerializedName

data class BillingAddress (

    @SerializedName("id"         ) var id        : String? = null,
    @SerializedName("address_1"  ) var address1  : String? = null,
    @SerializedName("address_2"  ) var address2  : String? = null,
    @SerializedName("suburb"     ) var suburb    : String? = null,
    @SerializedName("state_id"   ) var stateId   : String? = null,
    @SerializedName("state_name" ) var stateName : String? = null,
    @SerializedName("postcode"   ) var postcode  : String? = null

)