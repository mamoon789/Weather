package com.example.weather.domain.model

data class Weather(
    val current: Current,
    val location: Location
) : java.io.Serializable
