package com.example.thebooklibrary.network.response

import com.example.thebooklibrary.model.UserRegistrationData
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class UserRegistrationResponse(val data: UserRegistrationData, code: Int, status: String) :
    BaseResponse(code, status)