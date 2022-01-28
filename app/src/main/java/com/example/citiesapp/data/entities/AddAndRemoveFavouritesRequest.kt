package com.example.citiesapp.data.entities

import com.google.gson.annotations.SerializedName

data class AddAndRemoveFavouritesRequest (
    @SerializedName("cityId")
    val cityId: Int
    )