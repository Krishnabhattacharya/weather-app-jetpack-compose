package com.example.weatherapp.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.WeatherModel
import com.example.weatherapp.repository.RetrofitClint
import kotlinx.coroutines.launch

    class WeatherViewModel : ViewModel() {
        val weatherData = mutableStateOf<WeatherModel?>(null)
        val isLoading = mutableStateOf(false)
        val errorMessage = mutableStateOf<String?>(null)

        private val ApiService = RetrofitClint.apiService

        fun fetchData(key: String, city: String) {
            viewModelScope.launch {
                isLoading.value = true
                try {
                    Log.d("WeatherViewModel", "Fetching data for city: $city")
                    val res = ApiService.getWeatherReport(key, city)
                    if (res.isSuccessful) {
                        weatherData.value = res.body()
                        Log.d("WeatherViewModel->", "Data fetched successfully${res.body()}")
                    } else {
                        errorMessage.value = "Error: ${res.errorBody()?.string() ?: "Something went wrong"}"
                        Log.e("WeatherViewModel", "Error response: ${res.errorBody()}")
                    }
                } catch (e: Exception) {
                    errorMessage.value = "Exception: ${e.localizedMessage ?: "Unknown error"}"
                    Log.e("WeatherViewModel", "Exception: ${e.localizedMessage}")
                } finally {
                    isLoading.value = false
                    Log.d("WeatherViewModel", "Data fetching completed")
                }
            }}
    }


