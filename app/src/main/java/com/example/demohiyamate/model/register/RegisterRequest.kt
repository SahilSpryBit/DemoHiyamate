package com.hiyamate.model.register.request

data class RegisterRequest(
    val abn: String,
    val address: AddressX,
    val business_name: String,
    val business_type: String,
    val categories: List<Int>,
    val contact_no: String,
    val dob: String,
    val email: String,
    val first_name: String,
    val last_name: String,
    val password: String
)