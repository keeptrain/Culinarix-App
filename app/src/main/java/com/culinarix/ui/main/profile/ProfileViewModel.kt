package com.culinarix.ui.main.profile

import androidx.lifecycle.ViewModel
import com.culinarix.data.CulinarixRepository

class ProfileViewModel (private val repository: CulinarixRepository) : ViewModel() {

    fun getDetailUser() = repository.getDetailUser()

}