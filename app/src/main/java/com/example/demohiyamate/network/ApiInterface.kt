package com.example.demohiyamate.network

import com.example.demohiyamate.model.*
import com.example.demohiyamate.model.google.SearchAddressWithGoogle
import com.example.demohiyamate.model.google.latlon.GetLatLongById
import com.example.demohiyamate.model.login.LoginRequest
import com.example.demohiyamate.model.login.LoginResponseModel
import com.example.demohiyamate.model.register.EmailCheckRequest
import com.example.demohiyamate.model.register.EmailResponse
import com.example.demohiyamate.model.register.RegisterResponse
import com.hiyamate.model.register.request.RegisterRequest
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @FormUrlEncoded
    @POST("base_url.php")
    fun getBaseURL(
        @Field("application_platform") application_platform: String?,
        @Field("application_version") application_version: String?,
        @Field("application_type") application_type: String?
    ): Call<ModelClass?>?

    @POST("api/application/v1/company/login")
    fun login(
        @Header("Device-Type") device_type: String?,
        @Header("Device-Uuid") device_uuid: String?,
        @Body login: LoginRequest?
    ): Call<LoginResponseModel?>?

    @POST("api/application/v1/company/email/status/check")
    fun checkEmail(
        @Header("Device-Type") device_type: String?,
        @Header("Device-Uuid") device_uuid: String?,
        @Body emailCheckRequest: EmailCheckRequest?
    ): Call<EmailResponse?>?

    @POST("api/application/v1/company/join")
    fun register(
        @Header("Device-Type") device_type: String?,
        @Header("Device-Uuid") device_uuid: String?,
        @Body registerReq: RegisterRequest?
    ): Call<RegisterResponse?>?

    @GET("api/application/v1/get/categories")
    fun getCategory(
        @Header("Device-Type") device_type: String?,
        @Header("Device-Uuid") device_uuid: String?
    ): Call<GetCategoryRes?>?

    @POST("api/application/v1/get/common")
    fun commonApi(
        @Header("Tokenkey") Tokenkey: String?,
        @Header("Device-Type") device_type: String?,
        @Header("Device-Uuid") device_uuid: String?,
        @Body commonReq: CommonReq?
    ): Call<ModelClass?>?

    @GET("api/application/v1/secure/company/get")
    fun getCompanyUserInfo(
        @Header("Tokenkey") Tokenkey: String?,
    ): Call<GetCompyInfoRes?>?

    @GET("json?key=" + "AIzaSyDZEnXcomKBRmRo8RQ6jLnHP3ZUJuLjhmY" + "&components=country:AU")
    fun searchAddressByGoogleApi(@Query("input") input: String?): Call<SearchAddressWithGoogle?>?

    @GET("json?key=" + "AIzaSyDZEnXcomKBRmRo8RQ6jLnHP3ZUJuLjhmY")
    fun getLatlong(@Query("place_id") place_id: String?): Call<GetLatLongById?>?

    @GET("api/application/v1/get/state")
    fun getAllState(
        @Header("Device-Type") device_type: String?,
        @Header("Device-Uuid") device_uuid: String?
    ): Call<ModelClass?>?

    @POST("api/application/v1/company/forgot/password")
    fun forgotPassword(
        @Header("Device-Type") device_type: String?,
        @Header("Device-Uuid") device_uuid: String?,
        @Body forgotPasswordReq: ForgotPasswordReq?
    ): Call<ModelClass?>?

    @POST("api/application/v1/company/reset/password")
    fun resetPassword(
        @Header("Device-Type") device_type: String?,
        @Header("Device-Uuid") device_uuid: String?,
        @Body resetPasswordReq: ResetPasswordReq?
    ): Call<ModelClass?>?


}