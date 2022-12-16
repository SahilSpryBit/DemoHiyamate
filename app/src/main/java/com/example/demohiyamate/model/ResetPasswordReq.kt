package com.example.demohiyamate.model

data class ResetPasswordReq(
    val email: String,
    val otp: String,
    val password: String
)