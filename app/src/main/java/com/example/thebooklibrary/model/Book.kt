package com.example.thebooklibrary.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Book(
    @Json(name = "id") val id: Long?,
    @Json(name = "name") var name: String?,
    @Json(name = "owner_id") var ownerId: Long?,
    @Json(name = "created_at") val createdAt: String?,
    @Json(name = "updated_at") var updatedAt: String?,
    @Json(name = "status") var status: String?,
    @Json(name = "dead_line") var deadline: String?,
    @Json(name = "reader_user_id") var readerUserId: Long?
)