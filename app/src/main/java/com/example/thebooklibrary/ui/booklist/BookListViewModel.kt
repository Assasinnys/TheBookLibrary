package com.example.thebooklibrary.ui.booklist

import androidx.lifecycle.*
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.thebooklibrary.model.Book
import com.example.thebooklibrary.model.MainRepository
import com.example.thebooklibrary.network.response.BaseResponse
import com.example.thebooklibrary.network.response.BookListResponse
import com.example.thebooklibrary.network.response.ErrorResponse
import com.example.thebooklibrary.util.BookDataSource
import kotlinx.coroutines.launch
import javax.inject.Inject

class BookListViewModel @Inject constructor(private val repository: MainRepository) : ViewModel(), DefaultLifecycleObserver {

    private val _bookList = MutableLiveData<List<Book>>(mutableListOf())
    val bookList: LiveData<List<Book>> get() = _bookList

    private val _toastError = MutableLiveData<String>()
    val toastError: LiveData<String> get() = _toastError

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

//    override fun onCreate(owner: LifecycleOwner) {
//        requestBookList()
//    }

    val bookLiveData: LiveData<PagedList<Book>>

    init {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(10)
            .setInitialLoadSizeHint(10)
            .setPrefetchDistance(5)
            .build()
        bookLiveData = initializePagedListBuilder(config).build()
    }

    private fun initializePagedListBuilder(config: PagedList.Config): LivePagedListBuilder<Int, Book> {
        val dataSourceFactory = object : DataSource.Factory<Int, Book>() {
            override fun create(): DataSource<Int, Book> {
                return BookDataSource(repository)
            }
        }
        return LivePagedListBuilder(dataSourceFactory, config)
    }

    private fun requestBookList() {
        _isLoading.value = true
        viewModelScope.launch {
            val response = repository.getBookList(1)
            response?.let { checkResponse(it) }
            _isLoading.value = false
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