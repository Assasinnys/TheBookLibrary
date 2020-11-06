package com.example.thebooklibrary.network.response

import com.example.thebooklibrary.model.RegistrationErrorData
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
class ErrorRegistrationResponse(val data: RegistrationErrorData, code: Int, status: String) :
    BaseResponse(code, status) {
}