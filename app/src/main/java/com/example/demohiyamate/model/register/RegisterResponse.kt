package com.example.demohiyamate.model.register

import com.example.demohiyamate.model.Data

data class RegisterResponse(
    val `data`: Data,
    val error: String,
    val message: String,
    val status: Boolean,
    val status_code: Int
)