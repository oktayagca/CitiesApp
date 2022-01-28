package com.example.citiesapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.citiesapp.data.entities.AddAndRemoveFavouritesRequest
import com.example.citiesapp.data.entities.AuthRequest
import com.example.citiesapp.data.entities.AuthResponse
import com.example.citiesapp.data.repository.Repository
import com.example.citiesapp.ui.base.BaseViewModel
import com.example.citiesapp.utils.IS_GUEST
import com.example.citiesapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class MainActivitySharedViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {


    fun observeIsGuestUser(): LiveData<Boolean> {
        return liveData(Dispatchers.IO) {
            emit(isGuestUser())
        }
    }

    fun register(authRequest: AuthRequest): LiveData<Resource<AuthResponse>> {
        return repository.register(authRequest)
    }

    fun login(authRequest: AuthRequest): LiveData<Resource<AuthResponse>> {
        return repository.login(authRequest)
    }

    private fun isGuestUser() = repository.getBoolean(IS_GUEST)

    fun clearUserToken(key:String,value:String) {
        repository.saveString(key,value)
    }

    fun setIsGuestUser(value:Boolean){
        repository.saveBoolean(IS_GUEST,value)
    }

    fun addCityFavourites(request:AddAndRemoveFavouritesRequest)=
        repository.addCityFavourites(request)


    fun removeCityFavourites(request:AddAndRemoveFavouritesRequest)=
        repository.removeCityFavourites(request)

}

