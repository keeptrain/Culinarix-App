package com.culinarix.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.culinarix.data.CulinarixRepository
import kotlinx.coroutines.launch

class MainViewModel (private val repository: CulinarixRepository): ViewModel() {

    fun deleteSession() {
        viewModelScope.launch {
            repository.deleteSession()
        }
    }

}