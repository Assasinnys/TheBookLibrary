package com.example.thebooklibrary.model.datasources

interface AppPrefSource {
    fun saveToken(token: String)
    fun getToken(): String
    fun saveEmail(email: String)
    fun getEmail(): String
}