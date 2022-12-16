package com.example.demohiyamate.model

data class GetCategoryRes(
    val `data`: DataCategory?,
    val error: String?,
    val message: String?,
    val status: Boolean?,
    val status_code: Int?
)