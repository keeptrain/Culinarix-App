package com.culinarix.ui.main.contentbased

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.culinarix.data.CulinarixRepository
import com.culinarix.data.model.UserModel

class ContentBasedViewModel(private val repository : CulinarixRepository) : ViewModel() {
    fun getSession() : LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

}