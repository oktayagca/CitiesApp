package com.example.citiesapp.data.remote

import com.example.citiesapp.data.entities.AuthRequest
import com.example.citiesapp.utils.BaseDataSource

class RemoteDataSource(
    private val apiService: ApiService
) : BaseDataSource() {

    suspend fun guestAuth() = getResult {
        apiService.guestAuth()
    }

    suspend fun getCities() = getResult {
        apiService.getCities()
    }

    suspend fun register(authRequest: AuthRequest) = getResult{
        apiService.register(authRequest)
    }

    suspend fun login(authRequest: AuthRequest) = getResult {
        apiService.login(authRequest)
    }

    suspend fun getDistricts(cityId:String) = getResult {
        apiService.getDistricts(cityId)
    }

}