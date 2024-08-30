package com.example.weatherapp.presentation.weather.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherapp.domain.weather.WeatherData
import com.example.weatherapp.domain.weather.WeatherType
import com.example.weatherapp.presentation.ui.theme.Dark500
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun HourlyForecasts(
    modifier: Modifier = Modifier,
    weatherData: WeatherData,
) {
    val formattedTime = remember(weatherData) {
        weatherData.time.format(DateTimeFormatter.ofPattern("h a"))
    }
    Column(
        modifier = modifier
            .size(80.dp, 120.dp)
            .clip(shape = RoundedCornerShape(15.dp))
            .background(Dark500),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Text(text = formattedTime, color = White)
        Image(
            painter = painterResource(id = weatherData.weatherType.iconRes),
            contentDescription = null,
            modifier = Modifier.size(50.dp)
        )
        Text(
            text = "${weatherData.temperatureCelsius}°C", color = White,
            fontWeight = FontWeight.Bold
        )


    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HourlyForecastsPreview() {
    HourlyForecasts(
        weatherData = WeatherData(
            time = LocalDateTime.now(),
            temperatureCelsius = 25.0,
            pressure = 1013.0,
            windSpeed = 5.0,
            humidity = 60.0,
            weatherType = WeatherType.fromWMO(19)
        )
    )
}