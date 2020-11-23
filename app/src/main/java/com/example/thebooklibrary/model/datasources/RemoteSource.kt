package com.example.thebooklibrary.model.datasources

import com.example.thebooklibrary.network.response.*
import com.example.thebooklibrary.util.ResultData
import okhttp3.ResponseBody

interface RemoteSource {
    suspend fun login(login: String, pass: String): ResultData<UserLoginResponse>
    suspend fun register(login: String, pass: String): ResultData<UserRegistrationResponse>
    suspend fun getBookList(token: String, page: Int, limit: Int): ResultData<BookListResponse>
    suspend fun getBook(token: String, bookId: Long): ResultData<BookResponse>
    suspend fun getPersonalBooks(token: String): ResultData<BookListResponse>
    suspend fun sendNewBook(token: String, name: String): ResultData<ResponseBody>
    suspend fun returnBook(token: String, bookId: Long): ResultData<BookResponse>
    suspend fun userRead(token: String): ResultData<BookResponse>
}