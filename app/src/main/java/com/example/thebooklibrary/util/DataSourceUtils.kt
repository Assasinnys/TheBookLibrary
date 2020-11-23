package com.example.thebooklibrary.util

import android.util.Log
import com.example.thebooklibrary.network.response.BaseResponse
import com.example.thebooklibrary.network.response.ErrorRegistrationResponse
import com.example.thebooklibrary.network.response.ErrorResponse
import com.squareup.moshi.Moshi
import retrofit2.Response

sealed class ResultData<out T> {

    data class Success<out T>(val value: T) : ResultData<T>()

    data class Failure<out T>(val message: String) : ResultData<T>()

    companion object {

        fun <T> success(value: T): ResultData<T> = Success(value)

        fun <T> failure(errorMsg: String): ResultData<T> = Failure(errorMsg)

    }

}

abstract class BaseDataSource {

    private val moshi = Moshi.Builder().build()

    @Suppress("BlockingMethodInNonBlockingContext")
    protected suspend fun <T> getData(errorClass: Class<out BaseResponse>, call: suspend () -> Response<T>): ResultData<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                response.body()?.let {
                    return ResultData.success(it)
                }
                return ResultData.failure(response.message())
            }
            return formatError(response.errorBody()?.string(), errorClass)
        } catch (exception: Exception) {
            exception.printStackTrace()
            return formatError(null, null)
        }
    }

    private fun <T> formatError(errorString: String?, errorClass: Class<out BaseResponse>?): ResultData<T> {
        return when (val error =
            errorString?.let { moshi.adapter(errorClass).fromJson(it) }) {

            is ErrorResponse -> ResultData.failure(error.data)

            is ErrorRegistrationResponse -> ResultData.failure("${error.data.mail?.joinToString() ?: ""}|||${error.data.password?.joinToString() ?: ""}")

            else -> ResultData.failure(CONNECTION_ERROR)
        }
    }

}