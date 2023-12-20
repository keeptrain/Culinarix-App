package com.culinarix.data

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.liveData
import com.culinarix.data.Pref.UserPreference
import com.culinarix.data.api.response.auth.LoginResponse
import com.culinarix.data.api.response.auth.RegisterResponse
import com.culinarix.data.api.response.collab.CollabResponse
import com.culinarix.data.api.response.content.ContentBasedResponse
import com.culinarix.data.api.response.content.SearchContentResponse
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
    private val apiCollab: ApiService,
    private val pref: UserPreference
) {

    suspend fun deleteSession() {
        pref.deleteSession()
    }

    fun getSession(): Flow<UserModel> {
        return pref.getUser()
    }

    suspend fun saveSession(data: UserModel) {
        pref.saveSession(data)
    }


    //collab-filtering
    fun getTopRatedCollab() = liveData {
        emit(ResultState.Loading)
        try {
            val response = apiCollab.getTopRatedCollab()
            emit(ResultState.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, TopRatedResponse::class.java)
            val errorMessage = errorBody.message
            emit(ResultState.Error(errorMessage))
        } catch (e: Exception) {
            Log.d(TAG, "getTopCollab: ${e.message}", e)
            emit(ResultState.Error(TAG))
        }
    }


    fun getCollab(user_id: String) = liveData {
        emit(ResultState.Loading)
        try {
            val response = apiCollab.getCollaborative(user_id)
            emit(ResultState.Success(response))

        } catch (e: HttpException) {
            val jsonString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonString, CollabResponse::class.java)
            val errorMessage = errorBody.userId
            emit(ResultState.Error(errorMessage.toString()))

        } catch (e: Exception) {
            Log.d(TAG, "getCollab: ${e.message}", e)
            emit(ResultState.Error(TAG))

        }
    }

    //content-based
    fun getContentBased(place_name:String) = liveData {
        emit(ResultState.Loading)
        try {
            val response = apiContent.getContentBased(place_name)
            emit(ResultState.Success(response))

        }catch (e:HttpException){
            val jsonString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonString, ContentBasedResponse::class.java)
            val errorMessage = errorBody.message
            emit(ResultState.Error(errorMessage.toString()))

        }catch (e:Exception){
            Log.d(TAG, "getContent: ${e.message}", e)
            emit(ResultState.Error(TAG))
        }
    }

    fun searchContentBased(place_name: String) = liveData {
        emit(ResultState.Loading)
        try {
            val response = apiContent.searchContentBased(place_name)
            emit(ResultState.Success(response))

        }catch (e:HttpException){
            val jsonString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonString, SearchContentResponse::class.java)
            val errorMessage = errorBody.message
            emit(ResultState.Error(errorMessage.toString()))

        }catch (e:Exception){
            Log.d(TAG, "searchContent: ${e.message}", e)
            emit(ResultState.Error(TAG))

        }
    }

    //authentication
    fun getDetailUser() = liveData {
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

    fun login(email: String, password: String) = flow {
        emit(ResultState.Loading)
        try {
            val data = LoginModel(email, password)
            val response = apiService.login(data)
            val user = UserModel(response.data!!.userId, response.data.token, true)
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
            val data = SignupModel(name, email, password, address, age)
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
        fun getInstance(
            apiService: ApiService,
            pref: UserPreference,
            apiContent: ApiService,
            apiCollab: ApiService
        ): CulinarixRepository {
            return CulinarixRepository(apiService, apiContent, apiCollab, pref)
        }

    }

}