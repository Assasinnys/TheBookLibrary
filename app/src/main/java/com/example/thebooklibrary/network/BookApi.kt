package com.example.thebooklibrary.network

import com.example.thebooklibrary.network.request.UserAuthRequest
import com.example.thebooklibrary.network.response.BookListResponse
import com.example.thebooklibrary.network.response.UserLoginResponse
import com.example.thebooklibrary.network.response.UserRegistrationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface BookApi {

    @POST("api/v1/login")
    suspend fun login(@Body loginRequest: UserAuthRequest): Response<UserLoginResponse>

    @POST("api/v1/users")
    suspend fun register(@Body registerRequest: UserAuthRequest): Response<UserRegistrationResponse>

    @GET("api/v1/books")
    suspend fun getListOfBooks(@Query(value = "limit") limit: Int, @Query(value = "page") page: Int): Response<BookListResponse>

}