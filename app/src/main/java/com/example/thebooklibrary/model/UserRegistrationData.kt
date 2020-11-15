package com.example.thebooklibrary.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class UserRegistrationData(
    val id: Long,
    val mail: String,
    val token: String
)