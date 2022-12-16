package com.example.demohiyamate.model.google

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Prediction (

    @SerializedName("description"           ) var description          : String?                      = null,
    @SerializedName("matched_substrings"    ) var matchedSubstrings    : ArrayList<MatchedSubstring> = arrayListOf(),
    @SerializedName("place_id"              ) var placeId              : String?                      = null,
    @SerializedName("reference"             ) var reference            : String?                      = null,
    @SerializedName("structured_formatting" ) var structuredFormatting : StructuredFormatting?        = StructuredFormatting(),
    @SerializedName("terms"                 ) var terms                : ArrayList<Term>             = arrayListOf(),
    @SerializedName("types"                 ) var types                : ArrayList<String>            = arrayListOf()

)