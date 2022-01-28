package com.example.citiesapp.data.remote

import com.example.citiesapp.data.entities.*
import com.example.citiesapp.utils.NetworkConst
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @POST(NetworkConst.guestAuth)
    suspend fun guestAuth():Response<AuthResponse>
    @GET(NetworkConst.getCities)
    suspend fun getCities():Response<CitiesResponse>
    @POST(NetworkConst.register)
    suspend fun register(@Body authRequest: AuthRequest):Response<AuthResponse>
    @POST(NetworkConst.login)
    suspend fun login(@Body authRequest: AuthRequest):Response<AuthResponse>
    @GET("api/City/{cityId}/districts")
    suspend fun getDistricts(@Path("cityId")cityId:String):Response<DistrictsResponse>
    @GET(NetworkConst.favourites)
    suspend fun getFavourites():Response<CitiesResponse>
    @POST(NetworkConst.addFavourites)
    suspend fun addFavourites(@Body request:AddAndRemoveFavouritesRequest):Response<Any>
    @POST(NetworkConst.deleteFavourites)
    suspend fun deleteFavourites(@Body request:AddAndRemoveFavouritesRequest):Response<Any>
    @Multipart
    @Headers("Content-Type: multipart/form-data")
    @POST(NetworkConst.uploadDistrictImage)
    suspend fun uploadDistrictImage(@Part image:MultipartBody.Part):Response<Any>
    @POST(NetworkConst.createDistrict)
    suspend fun createDistrict(@Body request:CreateDiscrictRequest):Response<DistrictsResponseItem>
    @POST(NetworkConst.refreshToken)
    fun refreshToken(@Body request:RefreshTokenRequest):Call<AuthResponse>
}