package com.example.demohiyamate.model

import com.example.demohiyamate.model.BillingAddress
import com.example.demohiyamate.model.Category
import com.example.demohiyamate.model.Document
import com.example.demohiyamate.model.google.GetState
import com.example.demohiyamate.model.login.Address
import com.example.demohiyamate.model.login.Company
import com.example.demohiyamate.model.login.User
import com.google.gson.annotations.SerializedName

data class Data(

    @SerializedName("application_platform") var applicationPlatform: String? = null,
    @SerializedName("application_version") var applicationVersion: String? = null,
    @SerializedName("application_type") var applicationType: String? = null,
    @SerializedName("base_url") var baseUrl: String? = null,
    @SerializedName("key")
    var key: String? = null,
    @SerializedName("company")
    var company: Company? = Company(),
    @SerializedName("user") var user: User? = User(),
    @SerializedName("address") var address: Address? = Address(),
    @SerializedName("category") var category: ArrayList<Category> = arrayListOf(),
    @SerializedName("document") var document: Document? = Document(),
    @SerializedName("billing_address") var billingAddress: BillingAddress? = BillingAddress(),

    @SerializedName("GST_PERCENTAGE") var GSTPERCENTAGE: String? = null,
    @SerializedName("CURRENT_DATE_TIME") var CURRENTDATETIME: String? = null,
    @SerializedName("NOTIFICATION_UNREAD_COUNT") var NOTIFICATIONUNREADCOUNT: String? = null,
    @SerializedName("ANDROID_COMPANY_CURRENT_VERSION") var ANDROIDCOMPANYCURRENTVERSION: String? = null,
    @SerializedName("ANDROID_COMPANY_UPDATE_PRIORITY") var ANDROIDCOMPANYUPDATEPRIORITY: String? = null,
    @SerializedName("ANDROID_COMPANY_PLAYSTORE_URL") var ANDROIDCOMPANYPLAYSTOREURL: String? = null,
    @SerializedName("ANDROID_COMPANY_UPDATE_STATUS") var ANDROIDCOMPANYUPDATESTATUS: String? = null,
    @SerializedName("IOS_COMPANY_CURRENT_VERSION") var IOSCOMPANYCURRENTVERSION: String? = null,
    @SerializedName("IOS_COMPANY_UPDATE_PRIORITY") var IOSCOMPANYUPDATEPRIORITY: String? = null,
    @SerializedName("IOS_COMPANY_PLAYSTORE_URL") var IOSCOMPANYPLAYSTOREURL: String? = null,
    @SerializedName("IOS_COMPANY_UPDATE_STATUS") var IOSCOMPANYUPDATESTATUS: String? = null,
    @SerializedName("STRIPE_ID_VERIFICATION_STATUS") var STRIPEIDVERIFICATIONSTATUS: String? = null,
    @SerializedName("STRIPE_ID_VERIFICATION_STATUS_ID") var STRIPEIDVERIFICATIONSTATUSID: String? = null,
    @SerializedName("STRIPE_ID_VERIFICATION_STATUS_COLOR_CODE") var STRIPEIDVERIFICATIONSTATUSCOLORCODE: String? = null,
    @SerializedName("JOB_POST_STATUS") var JOBPOSTSTATUS: String? = null,
    @SerializedName("JOB_ACCEPTED") var JOBACCEPTED: String? = null,
    @SerializedName("MANUAL_VERIFICATION_STATUS") var MANUALVERIFICATIONSTATUS: String? = null,
    @SerializedName("ACCOUNT_DEACTIVATION_STATUS") var ACCOUNTDEACTIVATIONSTATUS: String? = null,
    @SerializedName("JOB_ACCEPTED_POST_STATUS_MESSAGE") var JOBACCEPTEDPOSTSTATUSMESSAGE: String? = null,

    @SerializedName("state" ) var state : ArrayList<GetState> = ArrayList()

)