package com.example.thebooklibrary.util

import androidx.room.TypeConverter
import com.squareup.moshi.*
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class RoomDateConverter {

    @TypeConverter
    fun dateToLong(date: Date): Long = date.time

    @TypeConverter
    fun longToDate(timeInMillis: Long): Date = Date(timeInMillis)
}

class MoshiDateConverter : JsonAdapter<Date>() {

    private val sdf = SimpleDateFormat(SERVER_DATE_FORMAT, Locale.getDefault())

    @FromJson
    override fun fromJson(reader: JsonReader): Date? {
        return try {
            val dateAsString = reader.nextString()
            sdf.parse(dateAsString)
        } catch (e: IOException) {
            null
        }
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: Date?) {}

    companion object {
        const val SERVER_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm"
    }
}