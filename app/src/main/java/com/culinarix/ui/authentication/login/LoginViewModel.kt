package com.culinarix.ui.authentication.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.culinarix.data.CulinarixRepository
import com.culinarix.data.api.response.LoginResponse
import com.culinarix.ui.utils.ResultState
import kotlinx.coroutines.launch

class LoginViewModel (private val repository : CulinarixRepository) : ViewModel() {

    private val _loginResult = MutableLiveData<ResultState<LoginResponse>>()
    val loginResult: LiveData<ResultState<LoginResponse>> = _loginResult

    fun login (email: String, password: String) {
        viewModelScope.launch {
            repository.login(email, password).collect {
                _loginResult.postValue(it)
            }
        }
    }
}