package com.example.citiesapp.data.entities


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CitiesResponseItem(
    @SerializedName("faved")
    val faved: Boolean,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("version")
    val version: Int
) : Parcelable