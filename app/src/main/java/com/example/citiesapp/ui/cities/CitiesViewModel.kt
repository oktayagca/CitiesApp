package com.example.citiesapp.ui.cities

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.citiesapp.data.entities.CitiesResponse
import com.example.citiesapp.data.repository.Repository
import com.example.citiesapp.ui.base.BaseViewModel
import com.example.citiesapp.utils.IS_GUEST
import com.example.citiesapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CitiesViewModel @Inject constructor(
    private val repository: Repository
) :BaseViewModel() {

    private var citiesList= MutableLiveData<Resource<CitiesResponse>>()

    fun observeCitiesList(): LiveData<Resource<CitiesResponse>> {
        if(citiesList.value == null){
            Log.v("cities","if")
            getCitiesList()
        }else{
            Log.v("cities","else")
        }
        return citiesList
    }

    private fun getCitiesList() {
        citiesList= repository.getCities() as MutableLiveData<Resource<CitiesResponse>>
    }

    fun isGuestUser() = repository.getBoolean(IS_GUEST)

    fun getDistricts(cityId:String) =
        repository.getDistricts(cityId)

}