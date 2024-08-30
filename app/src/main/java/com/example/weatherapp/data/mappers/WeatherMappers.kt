package com.example.weatherapp.data.mappers


import com.example.weatherapp.data.remote.DailyDataDto
import com.example.weatherapp.data.remote.HourlyDataDto
import com.example.weatherapp.data.remote.WeatherDto
import com.example.weatherapp.domain.weather.DailyTemperature
import com.example.weatherapp.domain.weather.WeatherData
import com.example.weatherapp.domain.weather.WeatherInfo
import com.example.weatherapp.domain.weather.WeatherType
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale


fun HourlyDataDto.toWeatherDataMap(): Map<Int, List<WeatherData>> {
    val weatherDataMap = mutableMapOf<Int, MutableList<WeatherData>>()

    for (index in time.indices) {

        val localDateTime = LocalDateTime.parse(time[index], DateTimeFormatter.ISO_DATE_TIME)

        val weatherData = WeatherData(
            time = localDateTime,
            temperatureCelsius = temperatures[index],
            pressure = pressures[index],
            windSpeed = windSpeeds[index],
            humidity = humidities[index],
            weatherType = WeatherType.fromWMO(weatherCodes[index])
        )
        // Compute the day key by dividing the index by 24 to group data by day.
        val groupKey = index / 24
        weatherDataMap.computeIfAbsent(groupKey) { mutableListOf() }.add(weatherData)
    }

    return weatherDataMap
}


fun DailyDataDto.toDailyTemperatureList(): List<DailyTemperature> {
    return days.mapIndexed { index, dayString ->
        DailyTemperature(
            day = LocalDate.parse(dayString).dayOfWeek.getDisplayName(
                TextStyle.FULL,
                Locale.getDefault()
            ),
            maxTemperature = maxTemperatures[index],
            minTemperature = minTemperatures[index],
            weatherType = WeatherType.fromWMO(weatherCodes[index])
        )
    }
}

fun WeatherDto.toWeatherInfo(): WeatherInfo {
    val weatherDataMap = hourlyData.toWeatherDataMap()
    val currentHour =
        if (LocalDateTime.now().minute < 30) LocalDateTime.now().hour else LocalDateTime.now().hour + 1

    // Accessing the current day's weather data, assuming the map starts with today as index 0.
    val currentWeatherData = weatherDataMap[0]?.find { it.time.hour == currentHour }


    return WeatherInfo(
        dailyWeatherData = weatherDataMap,
        currentWeatherData = currentWeatherData,
        dailyTemperature = dailyData.toDailyTemperatureList()
    )
}

