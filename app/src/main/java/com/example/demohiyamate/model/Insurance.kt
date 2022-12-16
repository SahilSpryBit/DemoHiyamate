package com.example.demohiyamate.model

import com.google.gson.annotations.SerializedName

data class Insurance (

    @SerializedName("id"        ) var id       : String? = null,
    @SerializedName("file"      ) var file     : String? = null,
    @SerializedName("file_name" ) var fileName : String? = null

)