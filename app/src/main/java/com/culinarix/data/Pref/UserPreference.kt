package com.culinarix.data.Pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.culinarix.data.model.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "data_user")

class UserPreference private constructor(private val dataStore : DataStore<Preferences>){

    suspend fun deleteSession() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    suspend fun saveSession (user : UserModel) {
        dataStore.edit { preferences ->
            preferences[TOKEN_USER_KEY] = user.token
            preferences[IS_LOGIN_KEY] = true
        }
    }

    fun getUser() : Flow<UserModel> {
        return dataStore.data.map { preferences ->
            UserModel(
                preferences[TOKEN_USER_KEY] ?: "",
                preferences[IS_LOGIN_KEY]  ?: false
            )
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        private val TOKEN_USER_KEY = stringPreferencesKey("token")
        private val IS_LOGIN_KEY = booleanPreferencesKey("isLogin")

        fun getInstance (dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }

    }
}