package com.example.weatherapp.repository

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitClint {
    private val baseUrl = "http://api.weatherstack.com/"
    val apiService: WeatherApiService by lazy {
        Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create())
            .build().create(WeatherApiService::class.java)
    }
}