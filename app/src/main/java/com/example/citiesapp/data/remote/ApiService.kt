package com.example.citiesapp.data.remote

import com.example.citiesapp.data.entities.CitiesResponse
import com.example.citiesapp.data.entities.AuthResponse
import com.example.citiesapp.data.entities.AuthRequest
import com.example.citiesapp.utils.NetworkConst
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST(NetworkConst.guestAuth)
    suspend fun guestAuth():Response<AuthResponse>
    @GET(NetworkConst.getCities)
    suspend fun getCities():Response<CitiesResponse>
    @POST(NetworkConst.register)
    suspend fun register(@Body authRequest: AuthRequest):Response<AuthResponse>
    @POST(NetworkConst.login)
    suspend fun login(@Body authRequest: AuthRequest):Response<AuthResponse>
}