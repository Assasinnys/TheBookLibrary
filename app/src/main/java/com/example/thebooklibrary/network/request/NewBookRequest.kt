package com.example.thebooklibrary.network.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewBookRequest (
    @Json(name = "name") val name: String
)