package com.culinarix.data.api.response.auth

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Data(

	@field:SerializedName("User_Id")
	val userId: String,

	@field:SerializedName("token")
	val token: String
)
