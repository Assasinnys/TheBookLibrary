package com.example.thebooklibrary.network.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
class UserLoginResponse(
    code: Int,
    status: String,
    val data: String
) : BaseResponse(code, status)