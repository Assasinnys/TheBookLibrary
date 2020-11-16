package com.example.thebooklibrary.model.datasources

import com.example.thebooklibrary.network.BookApi
import com.example.thebooklibrary.network.request.UserAuthRequest
import com.example.thebooklibrary.network.response.*
import com.example.thebooklibrary.util.BaseDataSource
import com.example.thebooklibrary.util.ResultData
import com.squareup.moshi.Moshi
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteSourceImpl @Inject constructor(private val api: BookApi) : RemoteSource, BaseDataSource() {

//    private val moshi = Moshi.Builder().build()

    override suspend fun getBook(token: String, bookId: Long): ResultData<BookResponse> {
        return getData(ErrorResponse::class.java) {
            api.getBook(token, bookId)
        }
    }

    override suspend fun login(login: String, pass: String): ResultData<UserLoginResponse> {
        return getData(ErrorResponse::class.java) {
            api.login(UserAuthRequest(login, pass))
        }
    }

    override suspend fun register(login: String, pass: String): ResultData<UserRegistrationResponse> {
        return getData(ErrorRegistrationResponse::class.java) {
            api.register(UserAuthRequest(login, pass))
        }
    }

    override suspend fun getBookList(token: String, page: Int, limit: Int): ResultData<BookListResponse> {
        return getData(ErrorResponse::class.java) {
            api.getListOfBooks(token, page, limit)
        }
    }

    /*private fun <T : BaseResponse> processResponse(response: Response<out BaseResponse>, errorClass: Class<T>): BaseResponse? {
        return if (response.isSuccessful) {
            response.body()
        } else {
            response.errorBody()?.string()?.let {
                moshi.adapter(errorClass).fromJson(it)
            }
        }
    }*/
}