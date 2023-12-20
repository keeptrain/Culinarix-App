package com.culinarix.data.api.retrofit

import com.culinarix.data.api.response.auth.LoginResponse
import com.culinarix.data.api.response.auth.RegisterResponse
import com.culinarix.data.api.response.auth.UserDetailResponse
import com.culinarix.data.api.response.collab.CollabResponse
import com.culinarix.data.api.response.content.ContentBasedResponse
import com.culinarix.data.api.response.content.SearchContentResponse
import com.culinarix.data.api.response.content.TopRatedResponse

import com.culinarix.data.model.LoginModel
import com.culinarix.data.model.SignupModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("top-rated")
    suspend fun getTopRatedContent(

    ): TopRatedResponse

    @GET("top-rated")
    suspend fun getTopRatedCollab(

    ): TopRatedResponse

    @GET("prediction/{user_id}")
    suspend fun getCollaborative(
        @Path("user_id") user_id: String

    ) : CollabResponse


    @GET("recommendations/{place_name}")
    suspend fun getContentBased(
        @Path("place_name") place_name : String

    ) : ContentBasedResponse


    @GET("search")
    suspend fun searchContentBased(
        @Query("query") query :String

    ) : SearchContentResponse


    @GET("user/details")
    suspend fun getDetailUser(
    ): UserDetailResponse


    @POST("auth/login")
    suspend fun login(
        @Body loginModel: LoginModel
    ): LoginResponse

    @POST("auth/register")
    suspend fun register(
        @Body signupModel: SignupModel
    ): RegisterResponse

}