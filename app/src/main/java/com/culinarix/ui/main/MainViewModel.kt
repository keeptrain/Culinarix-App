package com.culinarix.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.culinarix.data.CulinarixRepository
import com.culinarix.data.api.response.content.TopRatedResponse
import com.culinarix.ui.utils.ResultState
import kotlinx.coroutines.launch

class MainViewModel (private val repository: CulinarixRepository): ViewModel() {



    fun getTopRated() = repository.getTopRated()


    fun deleteSession() {
        viewModelScope.launch {
            repository.deleteSession()
        }
    }

}