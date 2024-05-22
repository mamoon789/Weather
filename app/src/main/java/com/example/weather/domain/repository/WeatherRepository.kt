package com.example.weather.domain.repository

import com.example.weather.data.model.WeatherDTO
import retrofit2.Response

interface WeatherRepository
{
    suspend fun getWeatherInfo(location: String): Response<WeatherDTO>
}