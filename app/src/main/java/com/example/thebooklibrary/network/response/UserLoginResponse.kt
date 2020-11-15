package com.example.thebooklibrary.network.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class UserLoginResponse(
    code: Int,
    status: String,
    val data: String
) : BaseResponse(code, status)