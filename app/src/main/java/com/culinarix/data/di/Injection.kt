package com.culinarix.data.di

import android.content.Context
import com.culinarix.data.CulinarixRepository
import com.culinarix.data.Pref.UserPreference
import com.culinarix.data.Pref.dataStore
import com.culinarix.data.api.retrofit.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {

    fun provideRepository(context: Context) : CulinarixRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getUser().first()  }
        val apiAuth = ApiConfig.getApiServiceAuth(user.token)
        val apiContent = ApiConfig.getApiServiceContent()
        val apiCollab = ApiConfig.getApiServiceCollab()
        return CulinarixRepository.getInstance(apiAuth,pref,apiContent, apiCollab)
    }

}