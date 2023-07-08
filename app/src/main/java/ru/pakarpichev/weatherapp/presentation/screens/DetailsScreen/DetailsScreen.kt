package ru.pakarpichev.weatherapp.presentation.screens.DetailsScreen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.pakarpichev.weatherapp.R
import ru.pakarpichev.weatherapp.presentation.navigation.Screens
import ru.pakarpichev.weatherapp.ui.theme.background
import ru.pakarpichev.weatherapp.ui.theme.card
import ru.pakarpichev.weatherapp.utils.Utils
import kotlin.math.roundToInt

@Composable
fun DetailsScreen(navController: NavController, cityId: String?) {
    val viewModel = hiltViewModel<DetailsViewModel>()
    val mContext = LocalContext.current
    val model = viewModel.data.observeAsState().value
    var icon by remember {
        mutableStateOf(1)
    }
    if (model == null) {
        LaunchedEffect(key1 = true) {
            viewModel.getData(
                cityId ?: "Saint Petersburg",
                mContext.getString(R.string.language),
                mContext
            )
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(backgroundColor = card) {
                IconButton(onClick = {
                    navController.popBackStack()
                }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_back),
                        contentDescription = "",
                        tint = Color.Black
                    )
                }
            }
        },
        backgroundColor = background
    ) {
        if (model == null) {
            Utils.Loading()
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    elevation = 10.dp,
                    backgroundColor = card,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 12.dp, top = 12.dp),
                                fontSize = 22.sp,
                                text = model.name,
                                textAlign = TextAlign.Start
                            )
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
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
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                                text = "${model.main.temp.roundToInt()}°C",
                            )
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 15.dp)
                            ) {
                                Text(
                                    fontSize = 16.sp,
                                    text = model.weather[0].description
                                )
                                Text(text = stringResource(id = R.string.feels_like) + " ${model.main.feels_like.roundToInt()}°C")
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            modifier = Modifier
                                .padding(top = 12.dp, start = 12.dp),
                            fontSize = 16.sp,
                            text = stringResource(id = R.string.visibility) + " ${
                                model.visibility.div(
                                    1000
                                )
                            } " + stringResource(id = R.string.length)
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                        ) {
                            Icon(
                                modifier = Modifier.padding(start = 5.dp),
                                painter = painterResource(id = R.drawable.wind),
                                contentDescription = ""
                            )
                            Text(
                                modifier = Modifier.padding(start = 3.dp),
                                text = model.wind.speed + " " + stringResource(id = R.string.wind_speed)
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
                            Icon(
                                painter = painterResource(id = R.drawable.pressure),
                                contentDescription = ""
                            )
                            Text(
                                modifier = Modifier.padding(start = 3.dp, end = 5.dp),
                                text = "${model.main.pressure} " + stringResource(id = R.string.pressure)
                            )
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
                when (model.weather.get(0).icon) {
                    "01d" -> icon = R.drawable.sun
                    "02d" -> icon = R.drawable.few_clouds
                    "03d" -> icon = R.drawable.cloudy
                    "04d" -> icon = R.drawable.most_cloudy
                    "09d" -> icon = R.drawable.rain
                    "10d" -> icon = R.drawable.rain_and_sun
                    "11d" -> icon = R.drawable.thunder
                    "13d" -> icon = R.drawable.snow
                    "50d" -> icon = R.drawable.fog
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .clickable {
                            viewModel.checkInternet(mContext)
                            if (viewModel.checkInternet(mContext)) {
                                navController.navigate(Screens.ForecastScreen.route + "/${cityId}")
                            } else {
                                Toast
                                    .makeText(
                                        mContext,
                                        mContext.getString(R.string.connectivity),
                                        Toast.LENGTH_SHORT
                                    )
                                    .show()
                            }
                        },
                    elevation = 10.dp,
                    backgroundColor = card,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp, bottom = 10.dp),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        text = stringResource(id = R.string.forecast)
                    )
                }
            }
        }
    }
}

