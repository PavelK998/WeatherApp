package ru.pakarpichev.weatherapp.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.pakarpichev.weatherapp.R
import ru.pakarpichev.weatherapp.domain.model.ForecastModel
import ru.pakarpichev.weatherapp.ui.theme.card
import kotlin.math.roundToInt


@Composable
fun WeatherCardModel(model: ForecastModel, id: String) {
    var icon by remember {
        mutableStateOf(1)
    }
    when (id) {
        "01d" -> icon = R.drawable.sun
        "02d" -> icon = R.drawable.few_clouds
        "03d" -> icon = R.drawable.cloudy
        "04d" -> icon = R.drawable.most_cloudy
        "09d" -> icon = R.drawable.rain
        "10d" -> icon = R.drawable.rain_and_sun
        "11d" -> icon = R.drawable.thunder
        "13d" -> icon = R.drawable.snow
        "50d" -> icon = R.drawable.fog
        "01n" -> icon = R.drawable.sun
        "02n" -> icon = R.drawable.few_clouds
        "03n" -> icon = R.drawable.cloudy
        "04n" -> icon = R.drawable.most_cloudy
        "09n" -> icon = R.drawable.rain
        "10n" -> icon = R.drawable.rain_and_sun
        "11n" -> icon = R.drawable.thunder
        "13n" -> icon = R.drawable.snow
        "50n" -> icon = R.drawable.fog
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        elevation = 5.dp,
        backgroundColor = card
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != 1) {
                Image(
                    modifier = Modifier
                        .height(55.dp)
                        .width(55.dp)
                        .padding(start = 5.dp),
                    painter = painterResource(id = icon), contentDescription = ""
                )
            }
            Text(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                text = "${model.main.temp.roundToInt()}°C"
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(3.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    text = model.dt_txt
                )
                Text(
                    modifier = Modifier.padding(start = 15.dp),
                    text = model.weather[0].description
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                ) {
                    Icon(
                        modifier = Modifier.padding(start = 5.dp, top = 8.dp),
                        painter = painterResource(id = R.drawable.wind), contentDescription = ""
                    )
                    Text(
                        modifier = Modifier.padding(start = 3.dp),
                        text = "${model.wind.speed} " + stringResource(id = R.string.wind_speed)
                    )
                    Spacer(
                        modifier = Modifier.weight(1f, true)
                    )
                    Icon(

                        painter = painterResource(id = R.drawable.humidity),
                        contentDescription = ""
                    )

                    Text(
                        modifier = Modifier.padding(start = 3.dp),
                        text = "${model.main.humidity}%"
                    )
                    Spacer(
                        modifier = Modifier.weight(1f, true)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.pressure),
                        contentDescription = ""
                    )
                    Text(
                        modifier = Modifier.padding(start = 3.dp, end = 5.dp),
                        text = "${model.main.pressure} " + stringResource(id = R.string.pressure)
                    )
                }
            }
        }
    }
}








