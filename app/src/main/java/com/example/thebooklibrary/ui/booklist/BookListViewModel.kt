package com.example.thebooklibrary.ui.booklist

import androidx.lifecycle.*
import com.example.thebooklibrary.model.Book
import com.example.thebooklibrary.model.MainRepository
import com.example.thebooklibrary.network.response.BaseResponse
import com.example.thebooklibrary.network.response.BookListResponse
import com.example.thebooklibrary.network.response.ErrorResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

class BookListViewModel @Inject constructor(private val repository: MainRepository) : ViewModel(), DefaultLifecycleObserver {

    private val _bookList = MutableLiveData<List<Book>>(mutableListOf())
    val bookList: LiveData<List<Book>> get() = _bookList

    private val _toastError = MutableLiveData<String>()
    val toastError: LiveData<String> get() = _toastError

    override fun onCreate(owner: LifecycleOwner) {
        requestBookList()
    }

    private fun requestBookList() {
        viewModelScope.launch {
            val response = repository.getBookList(1)
            response?.let { checkResponse(it) }
        }
    }

    private fun checkResponse(response: BaseResponse) {
        when(response) {
            is ErrorResponse -> {
                _toastError.value = response.data
            }
            is BookListResponse -> {
                _bookList.value = response.data
            }
        }
    }
}