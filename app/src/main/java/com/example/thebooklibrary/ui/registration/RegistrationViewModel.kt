package com.example.thebooklibrary.ui.registration

import android.util.Log
import androidx.lifecycle.*
import com.example.thebooklibrary.model.MainRepository
import javax.inject.Inject
import com.example.thebooklibrary.network.response.*
import com.example.thebooklibrary.util.ERR_EMPTY_FIELD
import com.example.thebooklibrary.util.ERR_PASS_DIFF
import com.example.thebooklibrary.util.NO_ERROR
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
    private val _loginErrorField = MutableLiveData(NO_ERROR)
    private val _passErrorField = MutableLiveData(NO_ERROR)
    private val _toastError = MutableLiveData<String>()
    private val _isLoading = MutableLiveData(false)

    val isRegistered: LiveData<Boolean> get() = _isRegistered
    val loginErrorField: LiveData<Int> get() = _loginErrorField
    val passErrorField: LiveData<Int> get() = _passErrorField
    val toastError: LiveData<String> get() = _toastError
    val isLoading: LiveData<Boolean> get() = _isLoading

    override fun onStart(owner: LifecycleOwner) {
        _isRegistered.value = false
        _toastError.value = ""
    }

    fun onRegisterClick() {
        val login = loginField.value
        val pass = passField.value
        val confPass = confPassField.value

        if (!isValidFields(login, pass, confPass)) return
        _isLoading.value = true
        viewModelScope.launch {
            repository.register(login!!, pass!!)?.let {
                checkResponse(it)
            }
            _isLoading.value = false
        }
    }

    private fun checkResponse(response: BaseResponse) {
        when(response) {
            is ErrorResponse -> {
                _toastError.value = response.data
            }
            is UserRegistrationResponse -> {
                _isRegistered.value = true
            }
            is ErrorRegistrationResponse -> {
                response.data.mail?.let {

                }
                Log.d("asd", "error: ${response.data.mail?.joinToString(" ")} + ${response.data.password?.joinToString(" ")}")
            }
        }
    }

    private fun isValidFields(login: String?, pass: String?, confPass: String?): Boolean {
        var isValid = true

        if (login.isNullOrEmpty()) {
            _loginErrorField.value = ERR_EMPTY_FIELD
            isValid = false
        } else {
            _loginErrorField.value = NO_ERROR
        }

        if (pass.isNullOrEmpty()) {
            _passErrorField.value = ERR_EMPTY_FIELD
            isValid = false
        } else {
            _passErrorField.value = NO_ERROR
        }

        if (pass != confPass) {
            _passErrorField.value = ERR_PASS_DIFF
            isValid = false
        }
        return isValid
    }
}
