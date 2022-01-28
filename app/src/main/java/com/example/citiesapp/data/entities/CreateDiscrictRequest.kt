package com.example.citiesapp.data.entities


import com.google.gson.annotations.SerializedName

data class CreateDiscrictRequest(
    @SerializedName("cityId")
    val cityId: Int?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("imageGUID")
    val imageGUID: String?,
    @SerializedName("name")
    val name: String?
)