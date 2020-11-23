package com.example.thebooklibrary.model.datasources

import com.example.thebooklibrary.network.BookApi
import com.example.thebooklibrary.network.request.NewBookRequest
import com.example.thebooklibrary.network.request.UserAuthRequest
import com.example.thebooklibrary.network.response.*
import com.example.thebooklibrary.util.BaseDataSource
import com.example.thebooklibrary.util.ResultData
import okhttp3.ResponseBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteSourceImpl @Inject constructor(private val api: BookApi) : RemoteSource, BaseDataSource() {

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

    override suspend fun getPersonalBooks(token: String): ResultData<BookListResponse> {
        return getData(ErrorResponse::class.java) {
            api.getPersonalBooks(token)
        }
    }

    override suspend fun sendNewBook(token: String, name: String): ResultData<ResponseBody> {
        return getData(ErrorResponse::class.java) {
            api.sendNewBook(token, NewBookRequest(name))
        }
    }

    override suspend fun returnBook(token: String, bookId: Long): ResultData<BookResponse> {
        return getData(ErrorResponse::class.java) {
            api.returnBook(token, bookId)
        }
    }

    override suspend fun userRead(token: String): ResultData<BookResponse> {
        return getData(ErrorResponse::class.java) {
            api.userRead(token)
        }
    }
}