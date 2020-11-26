package com.example.thebooklibrary.util

import androidx.room.TypeConverter
import com.example.thebooklibrary.R
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

    private companion object {
        const val SERVER_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm"
    }
}

class RoomEnumStatusConverter {
    @TypeConverter
    fun bookStatusToSomething(status: BookStatus): String {
        return status.toString()
    }

    @TypeConverter
    fun stringToBookStatus(status: String): BookStatus {
        return BookStatus.valueOf(status)
    }
}

/**
 * I didn't want to write the adapter for this enum because the statuses on the server is written in lower case. :)
 */

@Suppress("EnumEntryName")
enum class BookStatus {

    in_library {
        override fun asStringRes() = R.string.status_in_library
    },

    picked_up {
        override fun asStringRes() = R.string.status_picked_up
    },

    reserved {
        override fun asStringRes() = R.string.status_reserved
    };

    abstract fun asStringRes(): Int
}