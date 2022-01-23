package com.example.citiesapp.data.entities


import com.google.gson.annotations.SerializedName

data class CitiesResponseItem(
    @SerializedName("faved")
    val faved: Any,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("version")
    val version: Int
)