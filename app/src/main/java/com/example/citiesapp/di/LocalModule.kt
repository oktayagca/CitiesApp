package com.example.citiesapp.di

import android.content.Context
import com.example.citiesapp.data.local.LocalDataSource
import com.example.citiesapp.data.local.SharedPreferencesManager
import com.example.citiesapp.data.remote.ApiService
import com.example.citiesapp.data.remote.RemoteDataSource
import com.example.citiesapp.data.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferencesManager =
        SharedPreferencesManager(context)
    @Provides
    fun provideRemoteDataSource(
        apiService: ApiService
    ) : RemoteDataSource = RemoteDataSource(apiService)


    @Provides
    fun provideParamLocalDataSource(
        sharedPreferencesManager: SharedPreferencesManager
    ) : LocalDataSource = LocalDataSource(sharedPreferencesManager)

    @Provides
    fun provideParamRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): Repository {
        return Repository(remoteDataSource,localDataSource)
    }
}