package com.example.thebooklibrary.ui.bookdetails

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.thebooklibrary.R
import com.example.thebooklibrary.databinding.FragmentBookDetailsBinding
import com.example.thebooklibrary.util.daggerAppComponent
import com.example.thebooklibrary.util.toast
import javax.inject.Inject

class BookDetailsFragment : Fragment() {

    private lateinit var binding: FragmentBookDetailsBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: BookDetailsViewModel by viewModels { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        daggerAppComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_book_details, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModelObservers()
    }

    private fun setupViewModelObservers() {
        viewModel.apply {
            toastError.observe(viewLifecycleOwner) {
                toast(it)
            }
        }
    }
}