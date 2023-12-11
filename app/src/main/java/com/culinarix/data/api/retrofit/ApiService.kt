package com.culinarix.data.api.retrofit

import com.culinarix.data.api.response.LoginResponse
import com.culinarix.data.api.response.RegisterResponse
import com.culinarix.data.model.LoginModel
import com.culinarix.data.model.SignupModel
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("auth/login")
    suspend fun login(
        @Body loginModel : LoginModel
    ): LoginResponse

    @POST("auth/register")
    suspend fun register(
       @Body signupModel : SignupModel
    ) : RegisterResponse

}