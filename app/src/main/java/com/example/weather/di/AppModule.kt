package com.example.weather.di

import android.content.Context
import com.example.weather.common.Constants
import com.example.weather.data.remote.WeatherApi
import com.example.weather.data.repository.WeatherRepositoryImpl
import com.example.weather.domain.repository.WeatherRepository
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule
{
    @Singleton
    @Provides
    fun provideWeatherApi(): WeatherApi
    {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

    @Singleton
    @Provides
    fun provideWeatherRepository(api: WeatherApi): WeatherRepository
    {
        return WeatherRepositoryImpl(api)
    }
}