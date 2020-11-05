package com.example.thebooklibrary.network.request

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
data class UserAuthRequest(
    val mail: String,
    val password: String
)