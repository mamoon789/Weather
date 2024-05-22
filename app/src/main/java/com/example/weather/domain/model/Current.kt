package com.example.weather.domain.model

data class Current(
    val condition: Condition,
    val feelslike_c: Double,
    val feelslike_f: Double,
    val humidity: Double,
    val temp_c: Double,
    val temp_f: Double,
    val wind_kph: Double,
): java.io.Serializable
