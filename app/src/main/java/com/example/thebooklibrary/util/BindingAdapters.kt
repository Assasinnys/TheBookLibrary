package com.example.thebooklibrary.util

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.thebooklibrary.model.Book
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("app:errorText")
fun setErrorText(inputLayout: TextInputLayout, errorId: Int) {
    inputLayout.apply {
        error = context.resources.getString(errorId)
    }
}

@BindingAdapter("app:bookList")
fun setBookList(recyclerView: RecyclerView, books: List<Book>) {
    when (val adapter = recyclerView.adapter) {
        null -> recyclerView.adapter = BookListAdapter(books.toMutableList())
        is BookListAdapter -> adapter.setList(books)
    }
}