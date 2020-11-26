package com.example.thebooklibrary.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("app:errorText")
fun setErrorText(inputLayout: TextInputLayout, errorId: Int) {
    inputLayout.apply {
        error = context.resources.getString(errorId)
    }
}

@BindingAdapter("app:textFromRes")
fun setTextFromRes(textView: TextView, stringRes: Int) {
    if (stringRes > 0) textView.setText(stringRes)
}

/*
@BindingAdapter("app:bookList")
fun setBookList(recyclerView: RecyclerView, books: List<Book>) {
    when (val adapter = recyclerView.adapter) {
        null -> recyclerView.adapter = BookListAdapter(books.toMutableList())
        is BookListAdapter -> adapter.setList(books)
    }
}*/
