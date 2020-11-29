package com.example.thebooklibrary.ui.booklist

import android.content.Context
import android.os.Bundle
import android.view.*
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
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setupViewModelObservers()
        binding.rvBooks.adapter = BookPagingAdapter { v ->
            if (v.id == R.id.btn_reserve) {
                viewModel.reserveBook(v.tag)
            } else {
                viewModel.showBookDetails(v.tag)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_personal_book -> {
                findNavController().navigate(R.id.action_bookListFragment_to_personalBooksFragment)
                true
            }

            R.id.menu_profile -> {
                findNavController().navigate(R.id.action_bookListFragment_to_profileFragment)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupViewModelObservers() {
        viewModel.apply {
            toastRes.observe(viewLifecycleOwner) {
                toast(it)
            }
            toast.observe(viewLifecycleOwner) {
                toast(it)
            }
            bookLiveData.observe(viewLifecycleOwner) {
                (binding.rvBooks.adapter as BookPagingAdapter).submitList(it)
            }
            showBookDetails.observeSingle(viewLifecycleOwner) {
                if (it == true) findNavController().navigate(R.id.action_bookListFragment_to_bookDetailsFragment)
            }
        }
    }
}