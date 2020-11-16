package com.example.thebooklibrary.ui.registration

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
import com.example.thebooklibrary.databinding.FragmentRegistrationBinding
import com.example.thebooklibrary.di.ViewModelFactory
import com.example.thebooklibrary.util.daggerAppComponent
import com.example.thebooklibrary.util.toast
import javax.inject.Inject

class RegistrationFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: RegistrationViewModel by viewModels { viewModelFactory }

    private lateinit var binding: FragmentRegistrationBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        daggerAppComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_registration, container, false)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@RegistrationFragment.viewModel
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModelObservers()
    }

    private fun setupViewModelObservers() {
        viewModel.apply {
            isRegistered.observe(viewLifecycleOwner) {
                if(it){
                    toast("Registered")
                    findNavController().navigate(R.id.action_registrationFragment_to_bookListFragment)
                }
            }
            toastError.observe(viewLifecycleOwner) {
                toast(it)
            }
        }
    }
}