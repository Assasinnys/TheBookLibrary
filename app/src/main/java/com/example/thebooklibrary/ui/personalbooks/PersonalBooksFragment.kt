package com.example.thebooklibrary.ui.personalbooks

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
import com.example.thebooklibrary.databinding.FragmentPersonalBooksBinding
import com.example.thebooklibrary.di.ViewModelFactory
import com.example.thebooklibrary.util.BookListAdapter
import com.example.thebooklibrary.util.daggerAppComponent
import com.example.thebooklibrary.util.toast
import javax.inject.Inject

class PersonalBooksFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: PersonalBooksViewModel by viewModels { viewModelFactory }

    private lateinit var binding: FragmentPersonalBooksBinding

    override fun onAttach(context: Context) {
        daggerAppComponent().inject(this)
        lifecycle.addObserver(viewModel)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentPersonalBooksBinding>(
            inflater,
            R.layout.fragment_personal_books,
            container,
            false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@PersonalBooksFragment.viewModel
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModelObservers()
        binding.rvBooks.adapter = BookListAdapter {
            viewModel.showBookDetails(it.tag)
        }
    }

    private fun setupViewModelObservers() {
        viewModel.apply {
            toastError.observe(viewLifecycleOwner) {
                toast(it)
            }
            bookList.observe(viewLifecycleOwner) {
                (binding.rvBooks.adapter as BookListAdapter).setList(it)
            }
            showBookDetails.observe(viewLifecycleOwner) {
                if (it) findNavController().navigate(R.id.action_personalBooksFragment_to_bookDetailsFragment)
            }
        }
    }
}