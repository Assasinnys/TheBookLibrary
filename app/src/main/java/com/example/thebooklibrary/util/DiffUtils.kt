package com.example.thebooklibrary.util

import androidx.recyclerview.widget.DiffUtil
import com.example.thebooklibrary.model.Book

class BookDiffUtilCallback : DiffUtil.ItemCallback<Book>() {

    override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
        return oldItem == newItem
    }
}