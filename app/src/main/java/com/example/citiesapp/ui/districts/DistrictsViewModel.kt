package com.example.citiesapp.ui.districts

import com.example.citiesapp.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel


import com.example.citiesapp.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class DistrictsViewModel @Inject constructor(
    private val repository: Repository
): BaseViewModel() {

    fun getDistricts(cityId:String) =
        repository.getDistricts(cityId)
}