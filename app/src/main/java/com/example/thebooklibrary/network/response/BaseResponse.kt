package com.example.thebooklibrary.network.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
abstract class BaseResponse(
    val code: Int,
    val status: String
)