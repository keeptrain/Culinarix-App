package com.culinarix.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.culinarix.data.CulinarixRepository
import com.culinarix.data.di.Injection
import com.culinarix.ui.authentication.login.LoginViewModel
import com.culinarix.ui.authentication.signup.SignupViewModel
import com.culinarix.ui.main.MainViewModel
import com.culinarix.ui.main.contentbased.ContentBasedViewModel
import com.culinarix.ui.main.profile.ProfileViewModel

class ViewModelFactory (private val repository: CulinarixRepository): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {

            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(SignupViewModel::class.java) -> {
                SignupViewModel(repository) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(repository) as T
            }

            modelClass.isAssignableFrom(ContentBasedViewModel::class.java) -> {
                ContentBasedViewModel(repository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            return INSTANCE?:ViewModelFactory(Injection.provideRepository(context))
        }

    }
}