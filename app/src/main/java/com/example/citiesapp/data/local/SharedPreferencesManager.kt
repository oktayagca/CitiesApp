package com.example.citiesapp.data.local

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager(context: Context) {


    private val sharedPreferences: SharedPreferences =
    context.getSharedPreferences("Cities_App", Context.MODE_PRIVATE)

    fun saveString(key:String, value: String?) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getString(key:String): String? {
        return sharedPreferences.getString(key, "")
    }

    fun saveInt(key:String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }


    fun getInt(key:String): Int {
        return sharedPreferences.getInt(key, -1)
    }

    fun saveBoolean(key: String,value: Boolean){
        sharedPreferences.edit().putBoolean(key,value).apply()
    }

    fun getBoolean(key:String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    fun clearAllData(){
        sharedPreferences.edit().clear().apply()
    }




}