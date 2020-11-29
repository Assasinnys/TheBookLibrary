package com.example.thebooklibrary.ui.login

import androidx.lifecycle.*
import com.example.thebooklibrary.model.MainRepository
import com.example.thebooklibrary.network.response.BaseResponse
import com.example.thebooklibrary.network.response.ErrorResponse
import com.example.thebooklibrary.network.response.UserLoginResponse
import com.example.thebooklibrary.util.ERR_EMPTY_FIELD
import com.example.thebooklibrary.util.NO_ERROR
import com.example.thebooklibrary.util.ResultData
import com.example.thebooklibrary.util.SingleLiveEvent
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val repository: MainRepository) : ViewModel(),
    DefaultLifecycleObserver {

    val userLogin = MutableLiveData<String>()
    val userPass = MutableLiveData<String>()
    private val _loginErrorField = MutableLiveData(NO_ERROR)
    private val _passErrorField = MutableLiveData(NO_ERROR)
    private val _toastError = MutableLiveData<String>()
    private val _isLoading = MutableLiveData(false)

    val isLoggedIn = SingleLiveEvent<Boolean>()
    val loginErrorField: LiveData<Int> get() = _loginErrorField
    val passErrorField: LiveData<Int> get() = _passErrorField
    val toastError: LiveData<String> get() = _toastError
    val isLoading: LiveData<Boolean> get() = _isLoading

    override fun onCreate(owner: LifecycleOwner) {
        userLogin.value = repository.email
        isLoggedIn.value = repository.token.isNotEmpty()
    }

    override fun onStart(owner: LifecycleOwner) {
        _toastError.value = ""
    }

    fun userLoginTry() {
        val login = userLogin.value
        val pass = userPass.value

        if (!isValidFields(login, pass)) return

        _isLoading.value = true
        viewModelScope.launch {
            checkResult(repository.loginUser(login!!, pass!!))
            _isLoading.value = false
        }
    }

    private fun checkResult(resultData: ResultData<UserLoginResponse>) {
        when (resultData) {
            is ResultData.Success -> {
                repository.token = resultData.value.data
                repository.email = userLogin.value ?: ""
                isLoggedIn.value = true
            }
            is ResultData.Failure -> {
                _toastError.value = resultData.message
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