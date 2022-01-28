package com.example.citiesapp.utils

interface AppBarObservable {
    fun notifyObservers(change: Boolean, changedData: AppBarObserverEnum)
}