package com.culinarix.data.api.response.auth

import com.google.gson.annotations.SerializedName

data class UserDetailResponse(

	@field:SerializedName("Email")
	val email: String? = null,

	@field:SerializedName("Address")
	val address: String? = null,

	@field:SerializedName("User_Id")
	val userId: Int? = null,

	@field:SerializedName("Age")
	val age: Int? = null,

	@field:SerializedName("Name")
	val name: String? = null
)
