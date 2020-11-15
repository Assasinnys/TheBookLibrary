package com.example.thebooklibrary.network.response

import com.example.thebooklibrary.model.Book
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class BookResponse(val data: Book, code: Int, status: String) : BaseResponse(code, status)