package com.example.thebooklibrary.network.response

import com.example.thebooklibrary.model.Book
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
class BookListResponse(val data: List<Book>, code: Int, status: String) : BaseResponse(code, status)