package com.example.thebooklibrary.ui.bookdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thebooklibrary.model.Book
import com.example.thebooklibrary.model.MainRepository
import com.example.thebooklibrary.util.ResultData
import kotlinx.coroutines.launch
import javax.inject.Inject

class BookDetailsViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    private val _book = MutableLiveData<Book>()
    val book: LiveData<Book> get() = _book

    private val _toastError = MutableLiveData<String>()
    val toastError: LiveData<String> get() = _toastError

    init {
        requestSelectedBook()
    }

    private fun requestSelectedBook() {
        viewModelScope.launch {
            when (val response = repository.getBook()) {

                is ResultData.Success -> {
                    _book.value = response.value.data
                }

                is ResultData.Failure -> _toastError.value = response.message
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        repository.resetSelectedBook()
    }

}