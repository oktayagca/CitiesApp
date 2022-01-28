package com.example.citiesapp

import android.app.Application
import com.example.citiesapp.utils.AppBarObserver
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CitiesApplication: Application() {
    private val observerList =ArrayList<AppBarObserver>()

    override fun onCreate() {
        super.onCreate()
    }

    override fun onTerminate() {
        super.onTerminate()
    }

    fun getObserverList(): List<AppBarObserver> {
        return observerList
    }

    fun registerObserver(myObserver: AppBarObserver) {
        observerList.add(myObserver)
    }

    fun unRegisterObserver(myObserver: AppBarObserver) {
        observerList.remove(myObserver)
    }
}