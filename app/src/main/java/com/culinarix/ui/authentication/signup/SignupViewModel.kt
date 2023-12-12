package com.culinarix.ui.authentication.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.culinarix.data.CulinarixRepository
import com.culinarix.data.api.response.RegisterResponse
import com.culinarix.ui.utils.ResultState
import com.culinarix.ui.utils.customview.Domicile
import kotlinx.coroutines.launch

class SignupViewModel (private val repository: CulinarixRepository) : ViewModel(){

    private val _signUpResult: MutableLiveData<ResultState<RegisterResponse>> = MutableLiveData()
    val signUpResult: LiveData<ResultState<RegisterResponse>> = _signUpResult

    fun register (name: String, email: String, password: String, age: Int, domicile: String) {
        viewModelScope.launch {
            repository.register(name, email, password, age , domicile).collect {
                _signUpResult.postValue(it)
            }
        }
    }

}