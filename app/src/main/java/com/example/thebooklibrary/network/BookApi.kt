package com.example.thebooklibrary.network

import com.example.thebooklibrary.network.request.NewBookRequest
import com.example.thebooklibrary.network.request.UserAuthRequest
import com.example.thebooklibrary.network.response.*
import com.example.thebooklibrary.util.AUTHORIZATION_HEADER
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface BookApi {

    @POST("api/v1/login")
    suspend fun login(@Body loginRequest: UserAuthRequest): Response<UserLoginResponse>

    @POST("api/v1/users")
    suspend fun register(@Body registerRequest: UserAuthRequest): Response<UserRegistrationResponse>

    @GET("api/v1/books")
    suspend fun getListOfBooks(
        @Header(AUTHORIZATION_HEADER) bearerToken: String,
        @Query(value = "page") limit: Int,
        @Query(value = "limit") page: Int
    ): Response<BookListResponse>

    @GET("api/v1/books/{id}")
    suspend fun getBook(
        @Header(AUTHORIZATION_HEADER) bearerToken: String,
        @Path("id") id: Long
    ): Response<BookResponse>

    @GET("/api/v1/book/own_books")
    suspend fun getPersonalBooks(@Header(AUTHORIZATION_HEADER) bearerToken: String): Response<BookListResponse>

    @POST("/api/v1/books")
    suspend fun sendNewBook(
        @Header(AUTHORIZATION_HEADER) bearerToken: String,
        @Body newBookRequest: NewBookRequest
    ): Response<ResponseBody>

    @PUT("/api/v1/books/return/{id}")
    suspend fun returnBook(
        @Header(AUTHORIZATION_HEADER) bearerToken: String,
        @Path("id") bookId: Long
    ): Response<BookResponse>

    @GET("/api/v1/book/user_read")
    suspend fun userRead(@Header(AUTHORIZATION_HEADER) bearerToken: String): Response<BookResponse>

}