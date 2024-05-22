package com.example.weather.data.repository

import com.example.weather.data.model.WeatherDTO
import com.example.weather.data.remote.WeatherApi
import com.example.weather.domain.repository.WeatherRepository
import retrofit2.Response
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val weatherApi: WeatherApi): WeatherRepository
{
    override suspend fun getWeatherInfo(location: String): Response<WeatherDTO>
    {
        return weatherApi.getWeatherInfo(location)
    }
}