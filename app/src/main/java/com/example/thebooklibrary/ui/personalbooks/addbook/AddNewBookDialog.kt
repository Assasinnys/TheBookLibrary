package com.example.thebooklibrary.ui.personalbooks.addbook

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.thebooklibrary.R
import com.example.thebooklibrary.databinding.DialogAddPersonalBookBinding
import com.example.thebooklibrary.di.ViewModelFactory
import com.example.thebooklibrary.ui.personalbooks.PersonalBooksViewModel
import com.example.thebooklibrary.util.daggerAppComponent
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

class AddNewBookDialog : BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: PersonalBooksViewModel by viewModels { viewModelFactory }

    private lateinit var binding: DialogAddPersonalBookBinding

    override fun onAttach(context: Context) {
        daggerAppComponent().inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_add_personal_book, container, false)
        binding.apply {
            viewModel = this@AddNewBookDialog.viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }
}