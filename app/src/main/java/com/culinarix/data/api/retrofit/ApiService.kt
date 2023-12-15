package com.culinarix.data.api.retrofit

import com.culinarix.data.api.response.auth.LoginResponse
import com.culinarix.data.api.response.auth.RegisterResponse
import com.culinarix.data.api.response.auth.UserDetailResponse
import com.culinarix.data.api.response.content.TopRatedResponse

import com.culinarix.data.model.LoginModel
import com.culinarix.data.model.SignupModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("top-rated")
    suspend fun getTopRated (

    ) : TopRatedResponse

    @GET("user/details")
    suspend fun getDetailUser (
    ) : UserDetailResponse


    @POST("auth/login")
    suspend fun login(
        @Body loginModel : LoginModel
    ): LoginResponse

    @POST("auth/register")
    suspend fun register(
       @Body signupModel : SignupModel
    ) : RegisterResponse

}