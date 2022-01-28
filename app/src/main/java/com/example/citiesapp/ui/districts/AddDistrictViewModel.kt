package com.example.citiesapp.ui.districts

import com.example.citiesapp.data.entities.CreateDiscrictRequest
import com.example.citiesapp.data.repository.Repository
import com.example.citiesapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class AddDistrictViewModel @Inject constructor(
    private val repository: Repository
): BaseViewModel() {

    fun uploadDistrictImage(image:MultipartBody.Part) = repository.uploadDistrictImage(image)

    fun createDistrict(request: CreateDiscrictRequest) = repository.createDistricts(request)
}