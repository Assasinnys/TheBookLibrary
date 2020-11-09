package com.example.thebooklibrary.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thebooklibrary.databinding.ItemBookListBinding
import com.example.thebooklibrary.model.Book

class BookListAdapter(private val bookList: MutableList<Book> = mutableListOf()) : RecyclerView.Adapter<BookListAdapter.BookHolder>() {

    fun setList(newBookList: List<Book>) {
        bookList.apply {
            clear()
            addAll(newBookList)
        }
        notifyDataSetChanged()
    }

    class BookHolder(private val binding: ItemBookListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(book: Book) {
            binding.book = book
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolder {
        val binding = ItemBookListBinding.inflate(LayoutInflater.from(parent.context))
        return BookHolder(binding)
    }

    override fun onBindViewHolder(holder: BookHolder, position: Int) {
        holder.bind(bookList[position])
    }

    override fun getItemCount() = bookList.size
}