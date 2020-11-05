package com.example.thebooklibrary.di

import com.example.thebooklibrary.network.BookApi
import com.example.booklibrary.util.BASE_URL
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun getRetrofit(): BookApi {
        return Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create()).baseUrl(
            BASE_URL).build().create(BookApi::class.java)
    }
}