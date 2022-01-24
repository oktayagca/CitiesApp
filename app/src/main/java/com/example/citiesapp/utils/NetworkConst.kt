package com.example.citiesapp.utils

class NetworkConst {
    companion object{
        const val baseURL = "https://case.keove.com/"
        const val guestAuth = "guest/auth"
        const val getCities = "api/City"
        const val register = "user/sign-up"
        const val login = "/user/login"
        const val districts = "api/City/:cityId/districts"
    }
}