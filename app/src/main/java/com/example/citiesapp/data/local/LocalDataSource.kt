package com.example.citiesapp.data.local

class LocalDataSource(
private val sharedPreferencesManager: SharedPreferencesManager
) {
    fun getString(key:String)=
        sharedPreferencesManager.getString(key)

    fun saveString(key:String,value:String?)=
        sharedPreferencesManager.saveString(key,value)

    fun getInt(key:String)=
        sharedPreferencesManager.getString(key)

    fun saveInt(key:String,value:Int) =
        sharedPreferencesManager.saveInt(key,value)

    fun saveBoolean(key: String,value: Boolean) = sharedPreferencesManager.saveBoolean(key,value)

    fun getBoolean(key: String) = sharedPreferencesManager.getBoolean(key)

    fun clearAllData(){
        sharedPreferencesManager.clearAllData()
    }

}