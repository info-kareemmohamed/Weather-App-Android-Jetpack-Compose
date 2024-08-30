package com.example.weatherapp.domain.weather

data class DailyTemperature(
    val day: String,
    val maxTemperature: Double,
    val minTemperature: Double,
    val weatherType: WeatherType
)
