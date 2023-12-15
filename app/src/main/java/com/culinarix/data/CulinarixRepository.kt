package com.culinarix.data

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.liveData
import com.culinarix.data.Pref.UserPreference
import com.culinarix.data.api.response.auth.LoginResponse
import com.culinarix.data.api.response.auth.RegisterResponse
import com.culinarix.data.api.response.content.TopRatedResponse
import com.culinarix.data.api.retrofit.ApiService
import com.culinarix.data.model.LoginModel
import com.culinarix.data.model.SignupModel
import com.culinarix.data.model.UserModel
import com.culinarix.ui.utils.ResultState
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException

class CulinarixRepository private constructor(
    private val apiService: ApiService,
    private val apiContent: ApiService,
    private val pref: UserPreference ) {

    suspend fun deleteSession() {
        pref.deleteSession()
    }

    fun getSession() : Flow<UserModel> {
        return pref.getUser()
    }

    suspend fun saveSession (data: UserModel) {
        pref.saveSession(data)
    }


    fun getTopRated() = liveData {
        emit(ResultState.Loading)
        try {
            val response = apiContent.getTopRated()
            emit(ResultState.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, TopRatedResponse::class.java)
            val errorMessage = errorBody.message
            emit(ResultState.Error(errorMessage))
        } catch (e: Exception) {
            Log.d(TAG, "getStory: ${e.message}", e)
            emit(ResultState.Error(TAG))
        }
    }


    /*fun getContentBased() = flow {
        emit(ResultState.Loading)
        try {
            val data = LoginModel(email, password)
            val response = apiService.login(data)
            val user = UserModel(response.data!!.token,true)
            saveSession(user)
            emit(ResultState.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, LoginResponse::class.java)
            val errorMessage = errorBody.message
            emit(ResultState.Error(errorMessage))
        }
    }.flowOn(Dispatchers.IO)*/

    fun getDetailUser () = liveData {
        emit(ResultState.Loading)
        try {
            val response = apiService.getDetailUser()
            emit(ResultState.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, LoginResponse::class.java)
            val errorMessage = errorBody.message
            emit(ResultState.Error(errorMessage))
        }

    }

    fun login (email: String, password: String) = flow {
        emit(ResultState.Loading)
        try {
            val data = LoginModel(email, password)
            val response = apiService.login(data)
            val user = UserModel(response.data!!.userId, response.data.token,true)
            saveSession(user)
            emit(ResultState.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, LoginResponse::class.java)
            val errorMessage = errorBody.message
            emit(ResultState.Error(errorMessage))
        }
    }.flowOn(Dispatchers.IO)

    fun register(name: String, email: String, password: String, address: String, age: Int) = flow {
        emit(ResultState.Loading)
        try {
            val data = SignupModel(name,email,password,address, age)
            val response = apiService.register(data)
            emit(ResultState.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, RegisterResponse::class.java)
            val errorMessage = errorBody.message
            emit(ResultState.Error(errorMessage))
        }
    }.flowOn(Dispatchers.IO)


    companion object {
        fun getInstance(apiService: ApiService, pref: UserPreference,apiContent: ApiService): CulinarixRepository {
            return CulinarixRepository(apiService, apiContent,pref)
        }

    }

}