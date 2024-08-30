package com.example.weatherapp.domain.weather

data class WeatherInfo(
    val dailyWeatherData: Map<Int, List<WeatherData>>,
    val currentWeatherData: WeatherData?,
    val dailyTemperature:List <DailyTemperature>
)