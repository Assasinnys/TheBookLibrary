package com.example.thebooklibrary.model

import com.example.thebooklibrary.network.BookApi
import com.example.thebooklibrary.network.request.UserAuthRequest
import com.example.thebooklibrary.network.response.BaseResponse
import com.example.thebooklibrary.network.response.ErrorRegistrationResponse
import com.example.thebooklibrary.network.response.ErrorResponse
import com.squareup.moshi.Moshi
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(private val api: BookApi) {

    private val moshi = Moshi.Builder().build()

    suspend fun loginUser(login: String, pass: String): BaseResponse? {
        val request = UserAuthRequest(login, pass)
        return processResponse(api.login(request), ErrorResponse::class.java)
    }

    suspend fun register(login: String, pass: String): BaseResponse? {
        val request = UserAuthRequest(login, pass)
        return processResponse(api.register(request), ErrorRegistrationResponse::class.java)
    }

    private fun <T : BaseResponse> processResponse(response: Response<out BaseResponse>, errorClass: Class<T>): BaseResponse? {
        return if (response.isSuccessful) {
            response.body()
        } else {
            response.errorBody()?.string()?.let {
                moshi.adapter(errorClass).fromJson(it)
            }
        }
    }
}