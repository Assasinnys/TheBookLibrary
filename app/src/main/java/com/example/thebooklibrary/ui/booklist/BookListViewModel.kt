package com.example.thebooklibrary.ui.booklist

import androidx.lifecycle.*
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.thebooklibrary.model.Book
import com.example.thebooklibrary.model.MainRepository
import com.example.thebooklibrary.network.response.BookResponse
import com.example.thebooklibrary.util.BOOK_RESERVED
import com.example.thebooklibrary.util.BookDataSource
import com.example.thebooklibrary.util.ResultData
import kotlinx.coroutines.launch
import javax.inject.Inject

class BookListViewModel @Inject constructor(private val repository: MainRepository) : ViewModel(), DefaultLifecycleObserver {

    private val _toastRes = MutableLiveData<Int>()
    val toastRes: LiveData<Int> get() = _toastRes

    private val _toast = MutableLiveData<String>()
    val toast: LiveData<String> get() = _toast

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    val bookLiveData: LiveData<PagedList<Book>>

    private val _showBookDetails = MutableLiveData<Boolean>()
    val showBookDetails: LiveData<Boolean> get() = _showBookDetails

    init {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(10)
            .setInitialLoadSizeHint(10)
            .setPrefetchDistance(5)
            .build()
        bookLiveData = initializePagedListBuilder(config).build()
    }

    override fun onStart(owner: LifecycleOwner) {
        _toast.value = ""
        _toastRes.value = 0
        _showBookDetails.value = false
    }

    private fun initializePagedListBuilder(config: PagedList.Config): LivePagedListBuilder<Int, Book> {
        val dataSourceFactory = object : DataSource.Factory<Int, Book>() {
            override fun create(): DataSource<Int, Book> {
                return BookDataSource(repository)
            }
        }
        return LivePagedListBuilder(dataSourceFactory, config)
    }

    fun showBookDetails(id: Any) {
        if (id is Long) {
            repository.saveSelectedBook(id)
            _showBookDetails.value = true
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

            is ResultData.Failure -> _toast.value = result.message
        }
    }
}