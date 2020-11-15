package com.example.thebooklibrary.ui.bookdetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thebooklibrary.model.Book
import com.example.thebooklibrary.model.MainRepository
import com.example.thebooklibrary.network.response.BookResponse
import com.example.thebooklibrary.network.response.ErrorResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

class BookDetailsViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    private val _book = MutableLiveData<Book>()
    val book: LiveData<Book> get() = _book

    private val _toastError = MutableLiveData<String>()
    val toastError: LiveData<String> get() = _toastError

    private val _bookTitle = MutableLiveData<String>()
    val bookTitle: LiveData<String> get() = _bookTitle

    init {
        viewModelScope.launch {
            when (val response = repository.getBook()) {
                is BookResponse -> {
                    _book.value = response.data
                    Log.d("ASD", "${response.data.createdAt}")
                    _bookTitle.value = response.data.name
                }
                is ErrorResponse -> {
                    _toastError.value = response.data
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        repository.resetSelectedBook()
    }

}