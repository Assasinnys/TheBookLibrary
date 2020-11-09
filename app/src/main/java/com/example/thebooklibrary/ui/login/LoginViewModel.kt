package com.example.thebooklibrary.ui.login

import androidx.lifecycle.*
import com.example.thebooklibrary.model.MainRepository
import com.example.thebooklibrary.network.response.BaseResponse
import com.example.thebooklibrary.network.response.ErrorResponse
import com.example.thebooklibrary.network.response.UserLoginResponse
import com.example.thebooklibrary.util.ERR_EMPTY_FIELD
import com.example.thebooklibrary.util.NO_ERROR
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val repository: MainRepository) : ViewModel(), DefaultLifecycleObserver {

    val userLogin = MutableLiveData<String>()
    val userPass = MutableLiveData<String>()
    private val _isLoggedIn = MutableLiveData(false)
    private val _loginErrorField = MutableLiveData(NO_ERROR)
    private val _passErrorField = MutableLiveData(NO_ERROR)
    private val _toastError = MutableLiveData<String>()

//    val userLogin: LiveData<String> get() = _userLogin
//    val userPass: LiveData<String> get() = _userPass
    val isLoggedIn: LiveData<Boolean> get() = _isLoggedIn
    val loginErrorField: LiveData<Int> get() = _loginErrorField
    val passErrorField: LiveData<Int> get() = _passErrorField
    val toastError: LiveData<String> get() = _toastError

    override fun onCreate(owner: LifecycleOwner) {
        // TODO request previous login
    }

    override fun onStart(owner: LifecycleOwner) {
        _isLoggedIn.value = false
        _toastError.value = ""
    }

    fun userLoginTry() {
        val login = userLogin.value
        val pass = userPass.value

        if (isValidFields(login, pass)) {
            viewModelScope.launch {
                val response = repository.loginUser(login!!, pass!!)
                response?.let { checkResponse(it) }
            }
        }
    }

    private fun checkResponse(response: BaseResponse) {
        when(response) {
            is ErrorResponse -> {
                _toastError.value = response.data
            }
            is UserLoginResponse -> {
                repository.token = response.data
                _isLoggedIn.value = true
            }
        }
    }

    private fun isValidFields(login: String?, pass: String?): Boolean {
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

        return isValid
    }
}