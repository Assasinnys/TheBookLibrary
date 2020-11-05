package com.example.thebooklibrary.ui.registration

import androidx.lifecycle.*
import com.example.thebooklibrary.model.MainRepository
import javax.inject.Inject
import com.example.thebooklibrary.R

/**
 * '!!' used because this fields has been validated and can't be null.
 */

class RegistrationViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel(), DefaultLifecycleObserver {

    private val _iaRegistered = MutableLiveData(false)
    private val _loginErrorField = MutableLiveData(R.string.no_error)
    private val _passErrorField = MutableLiveData(R.string.no_error)

    val isRegistered: LiveData<Boolean> get() = _iaRegistered
    val loginErrorField: LiveData<Int> get() = _loginErrorField
    val passErrorField: LiveData<Int> get() = _passErrorField

    override fun onStart(owner: LifecycleOwner) {
        _iaRegistered.value = false
    }

    fun notifyRegistrationRequest(login: String?, pass: String?, confPass: String?) {
        if (!isValidFields(login, pass, confPass)) return

//        viewModelScope.launch {
//            val isSuccessful = repository.register(login!!, pass!!)
//            if (isSuccessful)
//                _iaRegistered.value = true
//            else
//                _loginErrorField.value = ERR_REG
//        }
    }

    private fun isValidFields(login: String?, pass: String?, confPass: String?): Boolean {
        var isValid = true

        if (login.isNullOrEmpty()) {
            _loginErrorField.value = R.string.error_empty_field
            isValid = false
        } else {
            _loginErrorField.value = R.string.no_error
        }

        if (pass.isNullOrEmpty()) {
            _passErrorField.value = R.string.error_empty_field
            isValid = false
        } else {
            _passErrorField.value = R.string.no_error
        }

        if (pass != confPass) {
            _passErrorField.value = R.string.error_pass_not_eq
            isValid = false
        }
        return isValid
    }
}
