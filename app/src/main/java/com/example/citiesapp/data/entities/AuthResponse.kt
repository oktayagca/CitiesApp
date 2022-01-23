package com.example.citiesapp.data.entities


import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("isGuest")
    val isGuest: Boolean,
    @SerializedName("refreshToken")
    val refreshToken: String,
    @SerializedName("token")
    val token: String
)