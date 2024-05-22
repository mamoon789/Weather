package com.example.weather.data.model

import com.example.weather.domain.model.Condition
import com.example.weather.domain.model.Current
import com.example.weather.domain.model.Location
import com.example.weather.domain.model.Weather
import com.google.gson.annotations.SerializedName

data class WeatherDTO(
    @SerializedName("current")
    val currentDTO: CurrentDTO,
    @SerializedName("location")
    val locationDTO: LocationDTO
)

fun WeatherDTO.toWeather(): Weather
{
    val location =
        Location(locationDTO.country, locationDTO.localtime, locationDTO.name, locationDTO.region)
    val condition = Condition(currentDTO.conditionDTO.icon, currentDTO.conditionDTO.text)
    val current = Current(
        condition,
        currentDTO.feelslike_c,
        currentDTO.feelslike_f,
        currentDTO.humidity,
        currentDTO.temp_c,
        currentDTO.temp_f,
        currentDTO.wind_kph
    )
    return Weather(current, location)
}