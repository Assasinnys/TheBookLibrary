package com.example.thebooklibrary.ui.registration

import androidx.lifecycle.*
import com.example.thebooklibrary.model.MainRepository
import javax.inject.Inject
import com.example.thebooklibrary.R
import com.example.thebooklibrary.network.response.BaseResponse
import com.example.thebooklibrary.network.response.ErrorResponse
import com.example.thebooklibrary.network.response.UserLoginResponse
import kotlinx.coroutines.launch

/**
 * '!!' used because these fields have been validated and can't be null.
 */

class RegistrationViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel(), DefaultLifecycleObserver {

    val loginField = MutableLiveData<String>()
    val passField = MutableLiveData<String>()
    val confPassField = MutableLiveData<String>()

    private val _isRegistered = MutableLiveData(false)
    private val _loginErrorField = MutableLiveData(R.string.no_error)
    private val _passErrorField = MutableLiveData(R.string.no_error)
    private val _toastError = MutableLiveData<String>()

    val isRegistered: LiveData<Boolean> get() = _isRegistered
    val loginErrorField: LiveData<Int> get() = _loginErrorField
    val passErrorField: LiveData<Int> get() = _passErrorField
    val toastError: LiveData<String> get() = _toastError

    override fun onStart(owner: LifecycleOwner) {
        _isRegistered.value = false
    }

    fun onRegisterClick() {
        val login = loginField.value
        val pass = passField.value
        val confPass = confPassField.value

        if (!isValidFields(login, pass, confPass)) return

        viewModelScope.launch {
            repository.register(login!!, pass!!)?.let {
                checkResponse(it)
            }
        }
    }

    private fun checkResponse(response: BaseResponse) {
        when(response) {
            is ErrorResponse -> {
                _toastError.value = response.data
            }
            is UserLoginResponse -> {
                _isRegistered.value = true
            }
        }
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
