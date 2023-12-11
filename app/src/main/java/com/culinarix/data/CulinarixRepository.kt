package com.culinarix.data

import com.culinarix.data.api.response.LoginResponse
import com.culinarix.data.api.response.RegisterResponse
import com.culinarix.data.api.retrofit.ApiService
import com.culinarix.data.model.LoginModel
import com.culinarix.data.model.SignupModel
import com.culinarix.ui.utils.ResultState
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException

class CulinarixRepository private constructor(private val apiService: ApiService) {

    fun login (email: String, password: String) = flow {
        emit(ResultState.Loading)
        try {
            val data = LoginModel(email, password)
            val response = apiService.login(data)
            //val user = UserModel(response.data.token,true)
            //saveSession(user)
            emit(ResultState.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, LoginResponse::class.java)
            val errorMessage = errorBody.message
            emit(ResultState.Error(errorMessage))
        }
    }.flowOn(Dispatchers.IO)

    fun register(name: String, email: String, password: String, age: Int, address: String) = flow {
        emit(ResultState.Loading)
        try {
            val data = SignupModel(name,email,password,age,address)
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
        @Volatile
        private var instance: CulinarixRepository? = null

        fun getInstance (apiService: ApiService) : CulinarixRepository =
            instance ?: synchronized(this) {
                instance ?: CulinarixRepository (apiService)
            }.also { instance = it }

        fun clearInstance() {
            instance = null
        }
    }

}