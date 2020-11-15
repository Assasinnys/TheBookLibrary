package com.example.thebooklibrary.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.thebooklibrary.R
import com.example.thebooklibrary.databinding.ItemBookListBinding
import com.example.thebooklibrary.model.Book

class BookPagingAdapter(private val listener: View.OnClickListener) : PagedListAdapter<Book, BookPagingAdapter.BookHolder>(BookDiffUtilCallback()){

    class BookHolder(private val binding: ItemBookListBinding, listener: View.OnClickListener) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener(listener)
        }

        fun bind(book: Book) {
            binding.apply {
                this.book = book
                root.tag = book.id
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolder {
        val binding = DataBindingUtil.inflate<ItemBookListBinding>(LayoutInflater.from(parent.context), R.layout.item_book_list, parent, false)
        return BookHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: BookHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
}