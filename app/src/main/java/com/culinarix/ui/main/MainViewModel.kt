package com.culinarix.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.culinarix.data.CulinarixRepository
import com.culinarix.data.api.response.content.TopRatedResponse
import com.culinarix.data.model.UserModel
import com.culinarix.ui.utils.ResultState
import kotlinx.coroutines.launch

class MainViewModel(private val repository: CulinarixRepository) : ViewModel() {


    fun getTopRatedCollab() = repository.getTopRatedCollab()

    fun getCollab(user_id:String) = repository.getCollab(user_id)

    fun getContent(place_name:String) = repository.getContentBased(place_name)

    fun searchContent(place_name: String) = repository.searchContentBased(place_name)

    fun getUserId(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun getDetailUser() = repository.getDetailUser()



}