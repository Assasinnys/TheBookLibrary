package com.example.thebooklibrary.model

import com.example.thebooklibrary.network.BookApi
import com.example.thebooklibrary.network.request.UserAuthRequest
import com.example.thebooklibrary.network.response.BaseResponse
import com.example.thebooklibrary.network.response.ErrorResponse
import com.squareup.moshi.Moshi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(private val api: BookApi) {

    private val moshi = Moshi.Builder().build()

    @Suppress("BlockingMethodInNonBlockingContext")
    suspend fun loginUser(login: String, pass: String): BaseResponse? {
        val request = UserAuthRequest(login, pass)
        val response = api.login(request)

        return if (response.isSuccessful) {
            response.body()
        } else {
            response.errorBody()?.string()?.let {
                moshi.adapter(ErrorResponse::class.java).fromJson(it)
            }
        }
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    suspend fun register(login: String, pass: String): BaseResponse? {
        val request = UserAuthRequest(login, pass)
        val response = api.register(request)

        return if (response.isSuccessful) {
            response.body()
        } else {
            response.errorBody()?.string()?.let {
                moshi.adapter(ErrorResponse::class.java).fromJson(it)
            }
        }
    }
}