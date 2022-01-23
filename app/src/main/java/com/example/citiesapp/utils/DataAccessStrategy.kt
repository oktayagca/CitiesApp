package com.example.citiesapp.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.citiesapp.data.entities.AuthResponse
import kotlinx.coroutines.Dispatchers

fun <T> performNetworkOperation(call: suspend () -> Resource<T>): LiveData<Resource<T>> {
    return liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val networkCall = call.invoke()
        if (networkCall.status == Resource.Status.SUCCESS) {
            val data = networkCall.data!!
            emit(Resource.success(data))
        } else if (networkCall.status == Resource.Status.ERROR) {
            emit(
                Resource.error(
                    networkCall.message!!,
                    networkCall.data,
                    networkCall.code
                )
            )
        }
    }
}

fun <T>performAuthNetworkOperation(
    call: suspend () -> Resource<T>,
    saveToken:(isGuest:Boolean,token:String,refreshToken:String)-> Unit,
):LiveData<Resource<T>>{
    return liveData(Dispatchers.IO){
        emit(Resource.loading())
        val networkCall = call.invoke()
        if (networkCall.status == Resource.Status.SUCCESS){
            val data = networkCall.data!!
            if (data is AuthResponse) {
                saveToken(data.isGuest,data.token,data.refreshToken)
            }
            emit(Resource.success(data))
        }else if(networkCall.status == Resource.Status.ERROR){
            emit(
                Resource.error(
                    networkCall.message!!,
                    networkCall.data,
                    networkCall.code
                )
            )
        }
    }
}