package com.example.thebooklibrary.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.thebooklibrary.database.dao.BooksDao
import com.example.thebooklibrary.model.datasources.RemoteSource
import com.example.thebooklibrary.network.response.*
import com.example.thebooklibrary.util.ResultData
import com.example.thebooklibrary.util.responseLiveData
import com.squareup.moshi.Moshi
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(private val remoteSource: RemoteSource, private val booksDao: BooksDao) {

    private val moshi = Moshi.Builder().build()
    var token: String = ""
        set(value) {
            field = "Bearer $value"
        }

    var email: String = ""

    private var selectedBookId: Long? = null

    suspend fun loginUser(login: String, pass: String): ResultData<UserLoginResponse> {
        return remoteSource.login(login, pass)
    }

    suspend fun register(login: String, pass: String): ResultData<UserRegistrationResponse> {
        return remoteSource.register(login, pass)
    }

    private fun <T : BaseResponse> processResponse(response: Response<out BaseResponse>, errorClass: Class<T>): BaseResponse? {
        return if (response.isSuccessful) {
            response.body()
        } else {
            response.errorBody()?.string()?.let {
                moshi.adapter(errorClass).fromJson(it)
            }
        }
    }

    suspend fun getBookList(page: Int, limit: Int): ResultData<BookListResponse> {
        return remoteSource.getBookList(token, page, limit)
    }

    fun getPersonalBooks() = responseLiveData(
        roomQuery = { booksDao.getPersonalBooks() },
        networkRequest = { remoteSource.getPersonalBooks(token) },
        roomSaveQuery = {
            booksDao.addPersonalBooks(
                it.data.map { book ->
                    book.apply { isPersonal = true }
                }
            )
        }
    )

    fun getBook2(): LiveData<ResultData<Book>> {
        selectedBookId?.let {id ->
            return responseLiveData(
                roomQuery = { booksDao.getBook(id) },
                networkRequest = { remoteSource.getBook(token, id) },
                roomSaveQuery = { booksDao.addPersonalBooks(listOf(it.data)) }
            )
        }
        return MutableLiveData(ResultData.failure("Book is not specified"))
    }

    suspend fun sendNewBook(name: String): ResultData<ResponseBody> {
        return remoteSource.sendNewBook(token, name)
    }

    suspend fun returnBook(bookId: Long): ResultData<BookResponse> {
        return remoteSource.returnBook(token, bookId)
    }

    suspend fun userRead(): ResultData<BookResponse> {
        return remoteSource.userRead(token)
    }

    suspend fun reserveBook(bookId: Long): ResultData<BookResponse> {
        return remoteSource.reserveBook(token, bookId)
    }

    fun saveSelectedBook(id: Long) {
        selectedBookId = id
    }

    fun resetSelectedBook() {
        selectedBookId = null
    }
}