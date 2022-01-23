package com.example.citiesapp.di

import androidx.viewbinding.BuildConfig
import com.example.citiesapp.data.local.SharedPreferencesManager
import com.example.citiesapp.data.remote.ApiService
import com.example.citiesapp.utils.GUEST_TOKEN
import com.example.citiesapp.utils.IS_GUEST
import com.example.citiesapp.utils.NetworkConst
import com.example.citiesapp.utils.TOKEN
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Named("baseUrl")
    fun provideBaseUrl():String = NetworkConst.baseURL

    @Singleton
    @Provides
    fun provideTokenInterceptor(
        sharedPref: SharedPreferencesManager
    ): Interceptor = Interceptor { chain ->
        val request = chain.request()
        val isGuest =sharedPref.getBoolean(IS_GUEST)
        var token:String? = null
        val newBuilder = request.newBuilder()
        token = if(isGuest){
            sharedPref.getString(GUEST_TOKEN)
        }else{
            sharedPref.getString(TOKEN)
        }
        if(!token.isNullOrEmpty()) {
            newBuilder.addHeader("Authorization", "Bearer $token")
        }
        chain.proceed(newBuilder.build())
    }


    @Provides
    @Singleton
    fun provideOkHttpClient(
        tokenInterceptor: Interceptor,
//        sessionInterceptor: SessionInterceptor
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level =
                    HttpLoggingInterceptor.Level.BODY
            })
            .addNetworkInterceptor(HttpLoggingInterceptor().apply {
                level =
                    HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(tokenInterceptor)
//            .addInterceptor(sessionInterceptor)

            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .callTimeout(60, TimeUnit.SECONDS)

        return builder.build()
    }

    @Singleton
    @Provides
    @Named("retrofit")
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        @Named("baseUrl") url: String,
        gson: Gson
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Singleton
    @Provides
    fun provideGson(): Gson = Gson()

    @Singleton
    @Provides
    fun provideApiService(@Named("retrofit") retrofit: Retrofit): ApiService =
        retrofit.create(
            ApiService::class.java
        )
}