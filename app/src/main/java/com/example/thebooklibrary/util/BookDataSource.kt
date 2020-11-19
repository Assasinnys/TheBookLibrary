package com.example.thebooklibrary.util

import androidx.paging.PageKeyedDataSource
import com.example.thebooklibrary.model.Book
import com.example.thebooklibrary.model.MainRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookDataSource (private val repository: MainRepository) :
    PageKeyedDataSource<Int ,Book>() {

    private val scope = CoroutineScope(Dispatchers.IO)

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Book>) {
        scope.launch {
            when (val books = repository.getBookList(START_PAGE, params.requestedLoadSize)) {
                is ResultData.Success -> {
                    callback.onResult(books.value.data, null, START_PAGE.plus(1))
                }
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Book>) {}

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Book>) {
        scope.launch {
            when (val books = repository.getBookList(params.key, params.requestedLoadSize)) {
                is ResultData.Success -> {
                    callback.onResult(books.value.data, if (books.value.data.isEmpty()) null else params.key.plus(1))
                }
            }
        }
    }

    companion object {
        private const val START_PAGE = 1
    }
}