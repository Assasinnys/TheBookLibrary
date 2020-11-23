package com.example.thebooklibrary.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.thebooklibrary.R
import com.example.thebooklibrary.databinding.ItemBookListBinding
import com.example.thebooklibrary.model.Book

class BookListAdapter(
    private val bookList: MutableList<Book> = mutableListOf(),
    private val itemClickListener: View.OnClickListener
) : RecyclerView.Adapter<BookListAdapter.BookHolder>() {

    fun setList(newBookList: List<Book>) {
        bookList.apply {
            clear()
            addAll(newBookList)
        }
        notifyDataSetChanged()
    }


    class BookHolder(private val binding: ItemBookListBinding, listener: View.OnClickListener) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener(listener)
        }

        fun bind(book: Book) {
            binding.book = book
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolder {
        val binding = DataBindingUtil.inflate<ItemBookListBinding>(LayoutInflater.from(parent.context), R.layout.item_book_list, parent, false)
        return BookHolder(binding, itemClickListener)
    }

    override fun onBindViewHolder(holder: BookHolder, position: Int) {
        holder.bind(bookList[position])
    }

    override fun getItemCount() = bookList.size
}