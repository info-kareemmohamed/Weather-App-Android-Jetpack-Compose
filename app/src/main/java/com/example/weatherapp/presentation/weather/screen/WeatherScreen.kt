package com.example.weatherapp.presentation.weather.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.R
import com.example.weatherapp.domain.weather.DailyTemperature
import com.example.weatherapp.domain.weather.WeatherData
import com.example.weatherapp.domain.weather.WeatherInfo
import com.example.weatherapp.domain.weather.WeatherType
import com.example.weatherapp.presentation.weather.viewmodel.WeatherState
import com.example.weatherapp.presentation.ui.theme.Dark200
import com.example.weatherapp.presentation.ui.theme.russoFont
import java.time.LocalDateTime
import kotlin.math.roundToInt


@Composable
fun WeatherScreen(
    modifier: Modifier = Modifier,
    state: WeatherState,
) {
    state.weatherInfo?.currentWeatherData?.let { data ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Dark200)
                .padding(top = 20.dp, start = 15.dp, end = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        )
        {
            Text(
                text = "${data.temperatureCelsius}°", color = White,
                fontSize = 70.sp, fontFamily = russoFont,
                modifier = Modifier.padding(bottom = 5.dp)
            )

            Image(
                painter = painterResource(
                    id = data.weatherType.iconRes
                ),
                contentDescription = "",
                modifier = Modifier.width(100.dp)

            )
            Text(
                text = data.weatherType.weatherDesc,
                fontSize = 40.sp, fontFamily = russoFont,
                color = White,
                modifier = Modifier.padding(bottom = 15.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                WeatherDataDisplay(
                    value = data.pressure.roundToInt(),
                    unit = "hpa",
                    icon = ImageVector.vectorResource(id = R.drawable.ic_pressure),
                    iconTint = White,
                    textStyle = TextStyle(color = White)
                )
                WeatherDataDisplay(
                    value = data.humidity.roundToInt(),
                    unit = "%",
                    icon = ImageVector.vectorResource(id = R.drawable.ic_drop),
                    iconTint = White,
                    textStyle = TextStyle(color = White)
                )
                WeatherDataDisplay(
                    value = data.windSpeed.roundToInt(),
                    unit = "km/h",
                    icon = ImageVector.vectorResource(id = R.drawable.ic_wind),
                    iconTint = White,
                    textStyle = TextStyle(color = White)
                )
            }

            Spacer(modifier = Modifier.height(15.dp))



            Text(
                "Hourly Forecasts:",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = White,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(bottom = 10.dp)
            )

            state.weatherInfo?.dailyWeatherData?.get(0)?.let { data ->
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(bottom = 15.dp)

                ) {
                    items(data) { weatherData ->
                        HourlyForecasts(weatherData = weatherData)
                    }
                }
            }

            Text(
                "Daily Forecasts:",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = White,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(bottom = 10.dp)
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(state.weatherInfo.dailyTemperature) { dailyTemperature ->
                    DailyForecasts(dailyTemperature = dailyTemperature)
                }
            }

        }

    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WeatherScreenPreview() {
    val sampleWeatherData = WeatherData(
        time = LocalDateTime.now(),
        temperatureCelsius = 25.0,
        pressure = 1013.0,
        windSpeed = 5.0,
        humidity = 60.0,
        weatherType = WeatherType.fromWMO(25)
    )

    val sampleDailyTemperature = DailyTemperature(
        day = "Monday",
        maxTemperature = 30.0,
        minTemperature = 20.0,
        weatherType = WeatherType.fromWMO(22)
    )

    val sampleWeatherInfo = WeatherInfo(
        dailyWeatherData = mapOf(
            0 to listOf(
                sampleWeatherData,
                sampleWeatherData,
                sampleWeatherData,
                sampleWeatherData,
                sampleWeatherData,
            )
        ),
        currentWeatherData = sampleWeatherData,
        dailyTemperature = listOf(
            sampleDailyTemperature,
            sampleDailyTemperature,
            sampleDailyTemperature,
            sampleDailyTemperature,
            sampleDailyTemperature
        )
    )

    WeatherScreen(
        state = WeatherState(
            weatherInfo = sampleWeatherInfo,
            isLoading = false,
            error = null
        )
    )
}