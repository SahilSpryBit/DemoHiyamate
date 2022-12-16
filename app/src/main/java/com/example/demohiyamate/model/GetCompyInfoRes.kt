package com.example.demohiyamate.model

data class GetCompyInfoRes(
    val `data`: Data,
    val error: String,
    val message: String,
    val status: Boolean,
    val status_code: Int
)