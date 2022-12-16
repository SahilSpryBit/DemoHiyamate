package com.hiyamate.model.register.request

data class AddressX(
    val address: String,
    val city: String,
    val lat: String,
    val lng: String,
    val post_code: String,
    val state_id: String,
    val suburb: String
)