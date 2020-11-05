package com.example.thebooklibrary.network.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
class ErrorResponse(val data: String, code: Int, status: String) : BaseResponse(code, status) {
}