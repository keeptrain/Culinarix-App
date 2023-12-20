package com.culinarix.data.api.response.collab

import com.google.gson.annotations.SerializedName

data class CollabResponse(

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("recommended_places")
	val recommendedPlaces: List<RecommendedPlacesItem?>? = null
)

data class RecommendedPlacesItem(

	@field:SerializedName("Gmaps_Address")
	val gmapsAddress: String? = null,

	@field:SerializedName("Category")
	val category: String? = null,

	@field:SerializedName("Description")
	val description: String? = null,

	@field:SerializedName("Address")
	val address: String? = null,

	@field:SerializedName("Image_Address")
	val imageAddress: String? = null,

	@field:SerializedName("Place_Id")
	val placeId: Int? = null,

	@field:SerializedName("Coordinate")
	val coordinate: String? = null,

	@field:SerializedName("Culinary_Ratings")
	val culinaryRatings: Double? = null,

	@field:SerializedName("Place_Name")
	val placeName: String? = null,

	@field:SerializedName("Long")
	val long: Double? = null,

	@field:SerializedName("Lat")
	val lat: Double? = null
)
