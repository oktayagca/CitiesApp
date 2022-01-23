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
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {
    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferencesManager =
        SharedPreferencesManager(context)

    @Singleton
    @Provides
    fun provideRemoteDataSource(
        apiService: ApiService
    ) : RemoteDataSource = RemoteDataSource(apiService)

    @Singleton
    @Provides
    fun provideParamLocalDataSource(
        sharedPreferencesManager: SharedPreferencesManager
    ) : LocalDataSource = LocalDataSource(sharedPreferencesManager)

    @Singleton
    @Provides
    fun provideParamRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): Repository {
        return Repository(remoteDataSource,localDataSource)
    }
}