package com.example.thebooklibrary.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.thebooklibrary.database.dao.BooksDao
import com.example.thebooklibrary.model.Book
import com.example.thebooklibrary.util.BOOK_DATABASE

@Database(entities = [Book::class], version = 1, exportSchema = false)
abstract class BookDatabase : RoomDatabase() {

    abstract fun booksDao() : BooksDao

    companion object {
        fun getInstance(context: Context): BookDatabase {
            return Room.databaseBuilder(context, BookDatabase::class.java, BOOK_DATABASE).allowMainThreadQueries().build()
        }
    }
}