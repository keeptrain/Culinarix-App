package com.culinarix.ui.authentication.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.culinarix.data.CulinarixRepository
import com.culinarix.data.api.response.auth.RegisterResponse
import com.culinarix.ui.utils.ResultState
import kotlinx.coroutines.launch

class SignupViewModel (private val repository: CulinarixRepository) : ViewModel(){

    private val _signUpResult: MutableLiveData<ResultState<RegisterResponse>> = MutableLiveData()
    val signUpResult: LiveData<ResultState<RegisterResponse>> = _signUpResult

    fun register (name: String, email: String, password: String, address: String, age: Int) {
        viewModelScope.launch {
            repository.register(name, email, password, address, age).collect {
                _signUpResult.postValue(it)
            }
        }
    }

}