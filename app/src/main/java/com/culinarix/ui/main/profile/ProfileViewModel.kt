package com.culinarix.ui.main.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.culinarix.data.CulinarixRepository
import kotlinx.coroutines.launch

class ProfileViewModel (private val repository: CulinarixRepository) : ViewModel() {

    fun getDetailUser() = repository.getDetailUser()

    fun deleteSession() {
        viewModelScope.launch {
            repository.deleteSession()
        }
    }

}