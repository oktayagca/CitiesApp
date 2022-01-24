package com.example.citiesapp.ui.cities

import com.example.citiesapp.data.entities.CitiesResponseItem

interface ICitiesListener {
    fun onClick(city:CitiesResponseItem)

}