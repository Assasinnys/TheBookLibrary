package com.example.thebooklibrary.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.thebooklibrary.model.Book
import com.example.thebooklibrary.util.BOOK_TABLE

@Dao
interface BooksDao {
    @Query("SELECT * FROM $BOOK_TABLE LIMIT :skip, :take")
    fun getBookPage(skip: Int, take: Int): LiveData<List<Book>>

    @Query("SELECT * FROM $BOOK_TABLE WHERE isPersonal = 1")
    fun getPersonalBooks(): LiveData<List<Book>>

    @Query("SELECT * FROM $BOOK_TABLE WHERE id = :id")
    fun getBook(id: Long): LiveData<Book>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPersonalBooks(books: List<Book>)
}