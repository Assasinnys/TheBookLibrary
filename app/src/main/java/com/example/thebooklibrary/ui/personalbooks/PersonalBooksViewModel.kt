package com.example.thebooklibrary.ui.personalbooks

import androidx.lifecycle.*
import com.example.thebooklibrary.model.Book
import com.example.thebooklibrary.model.MainRepository
import com.example.thebooklibrary.network.response.BookListResponse
import com.example.thebooklibrary.util.ResultData
import kotlinx.coroutines.launch
import javax.inject.Inject

class PersonalBooksViewModel @Inject constructor(private val repository: MainRepository) :
    ViewModel(), DefaultLifecycleObserver {

    private val _toastError = MutableLiveData<String>()
    val toastError: LiveData<String> get() = _toastError

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _showBookDetails = MutableLiveData<Boolean>()
    val showBookDetails: LiveData<Boolean> get() = _showBookDetails

    private val _bookList = MutableLiveData<List<Book>>()
    val bookList: LiveData<List<Book>> get() = _bookList

    override fun onStart(owner: LifecycleOwner) {
        requestPersonalBooks()
    }

    private fun requestPersonalBooks() {
        viewModelScope.launch {
            processResult(repository.getPersonalBooks())
        }
    }

    private fun processResult(result: ResultData<BookListResponse>) {
        when (result) {
            is ResultData.Success -> _bookList.value = result.value.data

            is ResultData.Failure -> _toastError.value = result.message
        }
    }

    fun showBookDetails(id: Any) {
        if (id is Long) {
            repository.saveSelectedBook(id)
            _showBookDetails.value = true
        }
    }
}