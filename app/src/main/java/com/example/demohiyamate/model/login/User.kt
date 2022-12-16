package com.example.demohiyamate.model.login

import com.google.gson.annotations.SerializedName

data class User (

    @SerializedName("id"         ) var id        : String? = null,
    @SerializedName("first_name" ) var firstName : String? = null,
    @SerializedName("last_name"  ) var lastName  : String? = null,
    @SerializedName("contact_no" ) var contactNo : String? = null,
    @SerializedName("email"      ) var email     : String? = null,
    @SerializedName("dob"        ) var dob       : String? = null

)