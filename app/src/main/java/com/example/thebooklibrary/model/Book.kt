package com.example.thebooklibrary.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.thebooklibrary.util.BOOK_TABLE
import com.example.thebooklibrary.util.BookStatus
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Date

@Entity(tableName = BOOK_TABLE)
@JsonClass(generateAdapter = true)
data class Book(
    @PrimaryKey
    @Json(name = "id") val id: Long,
    @Json(name = "name") var name: String?,
    @Json(name = "owner_id") var ownerId: Long?,
    @Json(name = "created_at") val createdAt: Date?,
    @Json(name = "updated_at") var updatedAt: Date?,
    @Json(name = "status") var status: BookStatus?,
    @Json(name = "dead_line") var deadline: String?,
    @Json(name = "reader_user_id") var readerUserId: Long?
)