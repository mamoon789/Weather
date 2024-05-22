package com.example.weather.domain.usecase.get_weatherInfo

import com.example.weather.common.Resource
import com.example.weather.data.model.toWeather
import com.example.weather.domain.model.Weather
import com.example.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetWeatherInfoUseCase @Inject constructor(private val weatherRepository: WeatherRepository)
{
    operator fun invoke(location: String): Flow<Resource<Weather>> = flow {
        try
        {
            emit(Resource.Loading())
            val response = weatherRepository.getWeatherInfo(location)
            if (response.isSuccessful)
            {
                val weather = response.body()!!.toWeather()
                emit(Resource.Success(weather))
            } else
            {
                emit(Resource.Error(response.message()))
            }
        } catch (e: IOException)
        {
            emit(Resource.Error(e.message ?: "Check your internet connection"))
        } catch (e: Exception)
        {
            emit(Resource.Error(e.message ?: "Something went wrong"))
        }
    }
}