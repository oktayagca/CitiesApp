package com.example.citiesapp.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.citiesapp.data.entities.AuthResponse
import com.example.citiesapp.data.repository.Repository
import com.example.citiesapp.ui.base.BaseViewModel
import com.example.citiesapp.utils.IS_GUEST
import com.example.citiesapp.utils.Resource
import com.example.citiesapp.utils.TOKEN
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {
    private var guestAuthData= MutableLiveData<Resource<AuthResponse>>()

    fun observeGuestAuthData():LiveData<Resource<AuthResponse>>{
        if(guestAuthData.value == null){
            guestAuth()
        }else{
            guestAuthData
        }
        return guestAuthData
    }

    private fun guestAuth(){
        guestAuthData= repository.guestAuth() as MutableLiveData<Resource<AuthResponse>>
    }

    fun isGuestUser() = repository.getBoolean(IS_GUEST)

}