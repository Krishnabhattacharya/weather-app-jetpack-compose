package com.example.weatherapp.repository

import com.example.weatherapp.model.WeatherModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("current")
    suspend fun getWeatherReport(
        @Query("access_key")apiKey:String,
        @Query("query")city:String
    ):Response<WeatherModel>
}