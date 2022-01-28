package com.example.citiesapp.data.remote

import com.example.citiesapp.data.entities.AddAndRemoveFavouritesRequest
import com.example.citiesapp.data.entities.AuthRequest
import com.example.citiesapp.data.entities.CreateDiscrictRequest
import com.example.citiesapp.utils.BaseDataSource
import okhttp3.MultipartBody

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

    suspend fun getFavourites() = getResult {
        apiService.getFavourites()
    }

    suspend fun addFavourites(request: AddAndRemoveFavouritesRequest) = getResult {
        apiService.addFavourites(request)
    }

    suspend fun deleteFavourites(request:AddAndRemoveFavouritesRequest) = getResult {
        apiService.deleteFavourites(request)
    }

    suspend fun uploadDistrictImage(image:MultipartBody.Part) = getResult {
        apiService.uploadDistrictImage( image)
    }

    suspend fun createDistricts(request:CreateDiscrictRequest) = getResult {
        apiService.createDistrict(request)
    }


}