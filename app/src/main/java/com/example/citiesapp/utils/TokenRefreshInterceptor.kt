package com.example.citiesapp.utils

import com.example.citiesapp.data.entities.AuthResponse
import com.example.citiesapp.data.entities.RefreshTokenRequest
import com.example.citiesapp.data.local.SharedPreferencesManager
import com.example.citiesapp.data.remote.ApiService
import com.google.gson.Gson
import okhttp3.*
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor


class TokenRefreshInterceptor (
    private val sharedPreferences:SharedPreferencesManager
):Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level =
                    HttpLoggingInterceptor.Level.BODY
            })

       val retrofit =  Retrofit.Builder()
            .baseUrl("https://case.keove.com/")
           .client(builder.build())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
        val apiService = retrofit.create(ApiService::class.java)

        val newTokenLoginModel:retrofit2.Response<AuthResponse>
        val request: Request = chain.request()
        val response: Response = chain.proceed(request)
        val rawJson: String? = response.body?.string()
        if (response.code == 401) {
            if(sharedPreferences.getBoolean(IS_GUEST)){
                newTokenLoginModel = apiService.refreshToken(RefreshTokenRequest(sharedPreferences.getString(GUEST_REFRESH_TOKEN))).execute()
                if(newTokenLoginModel.isSuccessful){
                    sharedPreferences.saveBoolean(IS_GUEST,newTokenLoginModel.body()!!.isGuest)
                    sharedPreferences.saveString(GUEST_REFRESH_TOKEN,newTokenLoginModel.body()!!.refreshToken)
                    sharedPreferences.saveString(GUEST_TOKEN,newTokenLoginModel.body()!!.token)
                }
            }else{
                newTokenLoginModel = apiService.refreshToken(RefreshTokenRequest(sharedPreferences.getString(REFRESH_TOKEN))).execute()
                if(newTokenLoginModel.isSuccessful){
                    sharedPreferences.saveBoolean(IS_GUEST,newTokenLoginModel.body()!!.isGuest)
                    sharedPreferences.saveString(REFRESH_TOKEN,newTokenLoginModel.body()!!.refreshToken)
                    sharedPreferences.saveString(TOKEN,newTokenLoginModel.body()!!.token)
                }
            }

            val newAuthenticationRequest = request.newBuilder().addHeader("Authorization", "Bearer " + newTokenLoginModel.body()?.token).build()
            return chain.proceed(newAuthenticationRequest)

        }
        return response.newBuilder().body(rawJson?.toResponseBody(response.body!!.contentType())).build()
    }
}