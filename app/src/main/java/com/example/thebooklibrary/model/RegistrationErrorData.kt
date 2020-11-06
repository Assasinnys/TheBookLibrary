package com.example.thebooklibrary.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
data class RegistrationErrorData (
    @Json(name = "mail") val mail: List<String>,
    @Json(name = "password") val password: List<String>
)