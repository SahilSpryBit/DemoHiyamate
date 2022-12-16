package com.example.demohiyamate.model

import com.google.gson.annotations.SerializedName

data class Document (

    @SerializedName("insurance"     ) var insurance    : ArrayList<Insurance>    = arrayListOf(),
    @SerializedName("trade_licence" ) var tradeLicence : ArrayList<TradeLicence> = arrayListOf(),
    @SerializedName("certificates"  ) var certificates : ArrayList<Certificates> = arrayListOf()

)