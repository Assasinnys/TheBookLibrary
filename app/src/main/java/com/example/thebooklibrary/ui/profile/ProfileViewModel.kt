package com.example.thebooklibrary.ui.profile

import android.view.View
import androidx.lifecycle.*
import com.example.thebooklibrary.model.Book
import com.example.thebooklibrary.model.MainRepository
import com.example.thebooklibrary.network.response.BookResponse
import com.example.thebooklibrary.util.ResultData
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(private val repository: MainRepository) : ViewModel(),
    DefaultLifecycleObserver {

    private val _toast = MutableLiveData<String>()
    val toast: LiveData<String> get() = _toast

    private val _email = MutableLiveData(repository.email)
    val email: LiveData<String> get() = _email

    private val _reservedBook = MutableLiveData<Book>()
    val reservedBook: LiveData<Book> get() = _reservedBook

    private val _bookVisibility = MutableLiveData(View.GONE)
    val bookVisibility: LiveData<Int> get() = _bookVisibility

    override fun onCreate(owner: LifecycleOwner) {
        viewModelScope.launch {
            val result = repository.userRead()
            checkFetchingBook(result)
        }
    }

    fun returnBook() {
        viewModelScope.launch {
            _reservedBook.value?.let {
                val result = repository.returnBook(it.id)
                checkReturning(result)
            }
        }
    }

    private fun checkFetchingBook(result: ResultData<BookResponse>) {
        when (result) {
            is ResultData.Success -> {
                _reservedBook.value = result.value.data
                _bookVisibility.value = View.VISIBLE
            }

            is ResultData.Failure -> {
//                _toast.value = result.message
            }
        }
    }

    private fun checkReturning(result: ResultData<BookResponse>) {
        when (result) {
            is ResultData.Success -> {
                _toast.value = "Book returned to library"
            }

            is ResultData.Failure -> {
                _toast.value = result.message
            }
        }
    }
}