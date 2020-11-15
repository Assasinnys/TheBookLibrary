package com.example.thebooklibrary.ui.booklist

import androidx.lifecycle.*
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.thebooklibrary.model.Book
import com.example.thebooklibrary.model.MainRepository
import com.example.thebooklibrary.util.BookDataSource
import javax.inject.Inject

class BookListViewModel @Inject constructor(private val repository: MainRepository) : ViewModel(), DefaultLifecycleObserver {

    private val _toastError = MutableLiveData<String>()
    val toastError: LiveData<String> get() = _toastError

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
}