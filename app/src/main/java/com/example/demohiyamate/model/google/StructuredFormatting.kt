package com.example.demohiyamate.model.google

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class StructuredFormatting (

    @SerializedName("main_text"                    ) var mainText                  : String?                              = null,
    @SerializedName("main_text_matched_substrings" ) var mainTextMatchedSubstrings : ArrayList<MainTextMatchedSubstring> = arrayListOf(),
    @SerializedName("secondary_text"               ) var secondaryText             : String?                              = null

)