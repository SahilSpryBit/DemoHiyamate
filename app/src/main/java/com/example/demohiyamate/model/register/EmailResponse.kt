package com.example.demohiyamate.model.register

data class EmailResponse(

    val `data`: EmailData,
    val error: String,
    val message: String,
    val status: Boolean,
    val status_code: Int

)