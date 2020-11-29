package com.example.thebooklibrary.ui.personalbooks

import androidx.lifecycle.*
import com.example.thebooklibrary.model.Book
import com.example.thebooklibrary.model.MainRepository
import com.example.thebooklibrary.network.response.BookResponse
import com.example.thebooklibrary.util.BOOK_RESERVED
import com.example.thebooklibrary.util.ERR_EMPTY_FIELD
import com.example.thebooklibrary.util.NO_ERROR
import com.example.thebooklibrary.util.ResultData
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import javax.inject.Inject

class PersonalBooksViewModel @Inject constructor(private val repository: MainRepository) :
    ViewModel(), DefaultLifecycleObserver {

    private val _toastError = MutableLiveData<String>()
    val toastError: LiveData<String> get() = _toastError

    private val _toastRes = MutableLiveData<Int>()
    val toastRes: LiveData<Int> get() = _toastRes

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _showBookDetails = MutableLiveData<Boolean>()
    val showBookDetails: LiveData<Boolean> get() = _showBookDetails

    private val _bookList = MutableLiveData<List<Book>>()
    val bookList: LiveData<List<Book>> get() = _bookList

    private val _errorBookNameField = MutableLiveData(NO_ERROR)
    val errorBookNameField: LiveData<Int> get() = _errorBookNameField

    val bookName = MutableLiveData<String>()

    override fun onStart(owner: LifecycleOwner) {
        _toastError.value = ""
        _toastRes.value = 0
        _showBookDetails.value = false
        requestPersonalBooks()
    }

    private fun requestPersonalBooks() {
        repository.getPersonalBooks().observeForever {
            processResult(it)
        }
    }

    private fun processResult(result: ResultData<List<Book>>) {
        when (result) {
            is ResultData.Success -> _bookList.value = result.value

            is ResultData.Failure -> _toastError.value = result.message
        }
    }

    fun showBookDetails(id: Any) {
        if (id is Long) {
            repository.saveSelectedBook(id)
            _showBookDetails.value = true
        }
    }

    fun addNewBook() {
        val book: String = bookName.value ?: ""

        if (isBookNameValid(book)) {
            sendNewBook(book)
        }
    }

    private fun sendNewBook(name: String) {
        viewModelScope.launch {
            val result = repository.sendNewBook(name)
            checkResult(result)
        }
    }

    private fun checkResult(result: ResultData<ResponseBody>) {
        when (result) {
            is ResultData.Failure -> {
                _toastError.value = result.message
            }
        }
    }

    private fun isBookNameValid(name: String): Boolean {
        return when {
            name.isEmpty() -> {
                _errorBookNameField.value = ERR_EMPTY_FIELD
                false
            }

            else -> {
                _errorBookNameField.value = NO_ERROR
                true
            }
        }
    }

    fun reserveBook(id: Any) {
        if (id is Long) {
            viewModelScope.launch {
                val result = repository.reserveBook(id)
                checkReserveResult(result)
            }
        }
    }

    private fun checkReserveResult(result: ResultData<BookResponse>) {
        when (result) {
            is ResultData.Success -> _toastRes.value = BOOK_RESERVED

            is ResultData.Failure -> _toastError.value = result.message
        }
    }
}