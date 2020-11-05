package com.example.thebooklibrary.ui.registration

import android.content.Context
import androidx.fragment.app.Fragment
import com.example.thebooklibrary.util.daggerAppComponent

class RegistrationFragment : Fragment() {

//    private lateinit var binding: FragmentRegistrationBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        daggerAppComponent().inject(this)
    }

//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        val binding =  DataBindingUtil.inflate(inflater, R.layout.fragment_registration, container, false)
//        )
//    }
}