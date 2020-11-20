package com.example.thebooklibrary.model

import com.example.thebooklibrary.model.datasources.RemoteSource
import com.example.thebooklibrary.network.BookApi
import com.example.thebooklibrary.network.request.UserAuthRequest
import com.example.thebooklibrary.network.response.*
import com.example.thebooklibrary.util.ResultData
import com.squareup.moshi.Moshi
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(private val api: BookApi, private val remoteSource: RemoteSource) {

    private val moshi = Moshi.Builder().build()
    var token: String = ""
        set(value) {
            field = "Bearer $value"
        }

    private var selectedBookId: Long? = null

    suspend fun loginUser(login: String, pass: String): ResultData<UserLoginResponse> {
        return remoteSource.login(login, pass)
    }

    suspend fun register(login: String, pass: String): ResultData<UserRegistrationResponse> {
        return remoteSource.register(login, pass)
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

    suspend fun getBookList(page: Int, limit: Int): ResultData<BookListResponse> {
        return remoteSource.getBookList(token, page, limit)
    }

    suspend fun getBook(): ResultData<BookResponse> {
        selectedBookId?.let { id ->
            return remoteSource.getBook(token, id)
        }
        return ResultData.failure("Book is not specified")
    }

    suspend fun getPersonalBooks(): ResultData<BookListResponse> {
        return remoteSource.getPersonalBooks(token)
    }

    fun saveSelectedBook(id: Long) {
        selectedBookId = id
    }

    fun resetSelectedBook() {
        selectedBookId = null
    }
}