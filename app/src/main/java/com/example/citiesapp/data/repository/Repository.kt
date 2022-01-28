package com.example.citiesapp.data.repository

import com.example.citiesapp.data.entities.AddAndRemoveFavouritesRequest
import com.example.citiesapp.data.entities.AuthRequest
import com.example.citiesapp.data.entities.CreateDiscrictRequest
import com.example.citiesapp.data.local.LocalDataSource
import com.example.citiesapp.data.remote.RemoteDataSource
import com.example.citiesapp.utils.*
import okhttp3.MultipartBody

class Repository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {

    fun guestAuth() = performAuthNetworkOperation(
        call = {
            remoteDataSource.guestAuth()
        },
        saveToken = { isGuest, guestToken, refreshToken ->
            saveBoolean(IS_GUEST, isGuest)
            saveString(GUEST_TOKEN, guestToken)
            saveString(GUEST_REFRESH_TOKEN, refreshToken)
        }
    )

    fun register(authRequest: AuthRequest) = performAuthNetworkOperation(
      call =   {
            remoteDataSource.register(authRequest)
        },
        saveToken = { isGuest, token, refreshToken ->
            saveBoolean(IS_GUEST, isGuest)
            if(isGuest){
                saveString(GUEST_TOKEN, token)
                saveString(GUEST_REFRESH_TOKEN, refreshToken)
            }else{
                saveString(TOKEN,token)
                saveString(REFRESH_TOKEN, refreshToken)
            }
        }
    )

    fun login(authRequest: AuthRequest) = performAuthNetworkOperation(
        call = {
            remoteDataSource.login(authRequest)
        },
        saveToken = { isGuest, token, refreshToken ->
            saveBoolean(IS_GUEST, isGuest)
            if(isGuest){
                saveString(GUEST_TOKEN, token)
                saveString(GUEST_REFRESH_TOKEN, refreshToken)
            }else{
                saveString(TOKEN,token)
                saveString(REFRESH_TOKEN, refreshToken)
            }
        }
    )

    fun getCities() = performNetworkOperation {
        remoteDataSource.getCities()
    }
    fun getDistricts(cityId:String)= performNetworkOperation{
        remoteDataSource.getDistricts(cityId)
    }
    fun getFavourites() = performNetworkOperation {
        remoteDataSource.getFavourites()
    }

    fun addCityFavourites(request: AddAndRemoveFavouritesRequest) = performNetworkOperation {
        remoteDataSource.addFavourites(request)
    }

    fun removeCityFavourites(request:AddAndRemoveFavouritesRequest)= performNetworkOperation {
        remoteDataSource.deleteFavourites(request)
    }

    fun uploadDistrictImage(image:MultipartBody.Part) = performNetworkOperation {
        remoteDataSource.uploadDistrictImage(image)
    }
    fun createDistricts(request: CreateDiscrictRequest) = performNetworkOperation {
        remoteDataSource.createDistricts(request)
    }

    fun getString(key: String) =
        localDataSource.getString(key)

    fun saveString(key: String, value: String?) =
        localDataSource.saveString(key, value)

    fun getInt(key: String) =
        localDataSource.getString(key)

    fun saveInt(key: String, value: Int) =
        localDataSource.saveInt(key, value)

    fun saveBoolean(key: String, value: Boolean) =
        localDataSource.saveBoolean(key, value)

    fun getBoolean(key: String) =
        localDataSource.getBoolean(key)

    fun clearAllSharedPrefData(){
        localDataSource.clearAllData()
    }




}