package com.example.demohiyamate.model.login

import com.google.gson.annotations.SerializedName

data class Company (

    @SerializedName("id"                         ) var id                       : String? = null,
    @SerializedName("name"                       ) var name                     : String? = null,
    @SerializedName("abn"                        ) var abn                      : String? = null,
    @SerializedName("business_type"              ) var businessType             : String? = null,
    @SerializedName("about"                      ) var about                    : String? = null,
    @SerializedName("manual_verification_status" ) var manualVerificationStatus : String? = null,
    @SerializedName("deactivate_request_status"  ) var deactivateRequestStatus  : String? = null,
    @SerializedName("status"                     ) var status                   : String? = null,
    @SerializedName("picture"                    ) var picture                  : String? = null

)