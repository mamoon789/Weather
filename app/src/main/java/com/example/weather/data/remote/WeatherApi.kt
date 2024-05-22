package com.example.weather.data.remote

import com.example.weather.common.Constants
import com.example.weather.data.model.WeatherDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi
{
    @GET("v1/current.json")
    suspend fun getWeatherInfo(
        @Query("q") location: String,
        @Query("key") key: String = Constants.API_KEY
    ): Response<WeatherDTO>
}