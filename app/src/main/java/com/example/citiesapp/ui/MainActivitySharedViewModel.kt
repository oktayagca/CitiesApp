package com.example.citiesapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.citiesapp.data.entities.AuthRequest
import com.example.citiesapp.data.entities.AuthResponse
import com.example.citiesapp.data.repository.Repository
import com.example.citiesapp.ui.base.BaseViewModel
import com.example.citiesapp.utils.Resource
import com.example.citiesapp.utils.TOKEN
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivitySharedViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {
    private var isAuthUser= MutableLiveData<Boolean>()
    init {
        Log.v("token",getUserToken().toString())
        if(getUserToken().isNullOrEmpty()){
            setUserAuthState(false)
        }else{
            setUserAuthState(true)
        }
    }

    fun observeIsAuthUser(): LiveData<Boolean> {
        return isAuthUser
    }

    fun register(authRequest: AuthRequest): LiveData<Resource<AuthResponse>> {
        isAuthUser.value = true
        return repository.register(authRequest)
    }

    fun login(authRequest: AuthRequest): LiveData<Resource<AuthResponse>> {
        isAuthUser.value = true
        return repository.login(authRequest)
    }



    private fun getUserToken() = repository.getString(TOKEN)

    fun clearUserToken(key:String,value:String) {
        repository.saveString(key,value)
    }

    fun setUserAuthState(isAuthUser:Boolean){
        this.isAuthUser.postValue(isAuthUser)
    }

    fun clearAllSharedPrefData(){
        repository.clearAllSharedPrefData()
    }
}

