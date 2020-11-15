package com.example.thebooklibrary.network.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserAuthRequest(
    @Json(name = "mail") val mail: String,
    @Json(name = "password") val password: String
)