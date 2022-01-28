package com.example.citiesapp.utils

class NetworkConst {
    companion object{
        const val baseURL = "https://case.keove.com/"
        const val guestAuth = "guest/auth"
        const val getCities = "api/City"
        const val register = "user/sign-up"
        const val login = "/user/login"
        const val districts = "api/City/:cityId/districts"
        const val favourites = "api/City/favorites"
        const val deleteFavourites = "api/City/delete-favorite"
        const val addFavourites = "api/City/add-favorite"
        const val createDistrict = "api/City/create-district"
        const val uploadDistrictImage = "api/City/upload-district-image"
        const val refreshToken = "/refresh-token"
    }
}