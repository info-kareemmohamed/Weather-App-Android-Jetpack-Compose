package com.example.weatherapp.presentation.weather.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ArrowDownward
import androidx.compose.material.icons.sharp.ArrowUpward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherapp.domain.weather.DailyTemperature
import com.example.weatherapp.domain.weather.WeatherType
import com.example.weatherapp.presentation.ui.theme.Dark500


@Composable
fun DailyForecasts(
    modifier: Modifier = Modifier,
    dailyTemperature: DailyTemperature
) {


    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(shape = RoundedCornerShape(15.dp))
            .background(Dark500)
            .padding(start = 16.dp, end = 8.dp)
    ) {
        Text(
            text = dailyTemperature.day,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterStart)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.Center)

        ) {
            Icon(Icons.Sharp.ArrowDownward, tint = Color(0xffff5353), contentDescription = null)
            Text(
                text = "${dailyTemperature.minTemperature}°",
                color = Color.White,
                modifier = Modifier.padding(end = 8.dp)
            )
            Icon(Icons.Sharp.ArrowUpward, tint = Color(0xff2eff8c), contentDescription = null)
            Text(text = "${dailyTemperature.maxTemperature}°", color = Color.White)
        }

        Image(
            painter = painterResource(
                id = dailyTemperature.weatherType.iconRes
            ),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .width(35.dp)
        )
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DailyForecastsPreview() {
    DailyForecasts(
        dailyTemperature = DailyTemperature(
            day = "Monday",
            maxTemperature = 30.0,
            minTemperature = 20.0,
            weatherType = WeatherType.fromWMO(16)
        )
    )
}