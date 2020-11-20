package com.example.thebooklibrary.network

import com.example.thebooklibrary.network.request.UserAuthRequest
import com.example.thebooklibrary.network.response.BookListResponse
import com.example.thebooklibrary.network.response.BookResponse
import com.example.thebooklibrary.network.response.UserLoginResponse
import com.example.thebooklibrary.network.response.UserRegistrationResponse
import retrofit2.Response
import retrofit2.http.*

interface BookApi {

    @POST("api/v1/login")
    suspend fun login(@Body loginRequest: UserAuthRequest): Response<UserLoginResponse>

    @POST("api/v1/users")
    suspend fun register(@Body registerRequest: UserAuthRequest): Response<UserRegistrationResponse>

    @GET("api/v1/books")
    suspend fun getListOfBooks(
        @Header("Authorization") bearerToken: String,
        @Query(value = "page") limit: Int,
        @Query(value = "limit") page: Int
    ): Response<BookListResponse>

    @GET("api/v1/books/{id}")
    suspend fun getBook(
        @Header("Authorization") bearerToken: String,
        @Path("id") id: Long
    ): Response<BookResponse>

    @GET("/api/v1/book/own_books")
    suspend fun getPersonalBooks(@Header("Authorization") bearerToken: String): Response<BookListResponse>

}