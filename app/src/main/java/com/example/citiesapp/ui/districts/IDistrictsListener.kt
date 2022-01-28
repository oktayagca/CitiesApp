package com.example.citiesapp.ui.districts

import com.example.citiesapp.data.entities.DistrictsResponseItem

interface IDistrictsListener {
    fun onClick(district: DistrictsResponseItem)
    fun onClickAddButton()
}