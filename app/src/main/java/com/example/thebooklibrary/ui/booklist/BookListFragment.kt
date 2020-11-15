package com.example.thebooklibrary.ui.booklist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.thebooklibrary.R
import com.example.thebooklibrary.databinding.FragmentBookListBinding
import com.example.thebooklibrary.di.ViewModelFactory
import com.example.thebooklibrary.util.BookPagingAdapter
import com.example.thebooklibrary.util.daggerAppComponent
import com.example.thebooklibrary.util.toast
import javax.inject.Inject

class BookListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: BookListViewModel by viewModels { viewModelFactory }

    private lateinit var binding: FragmentBookListBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        daggerAppComponent().inject(this)
        lifecycle.addObserver(viewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentBookListBinding>(
            inflater,
            R.layout.fragment_book_list,
            container,
            false
        ).apply {
            viewModel = this@BookListFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
            executePendingBindings()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModelObservers()
        binding.rvBooks.adapter = BookPagingAdapter {
            viewModel.showBookDetails(it.tag)
        }
    }

    private fun setupViewModelObservers() {
        viewModel.apply {
            toastError.observe(viewLifecycleOwner) {
                toast(it)
            }
            bookLiveData.observe(viewLifecycleOwner) {
                (binding.rvBooks.adapter as BookPagingAdapter).submitList(it)
            }
            showBookDetails.observe(viewLifecycleOwner) {
                if (it) findNavController().navigate(R.id.action_bookListFragment_to_bookDetailsFragment)
            }
        }
    }
}