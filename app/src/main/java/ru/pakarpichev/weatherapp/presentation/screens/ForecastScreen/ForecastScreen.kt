package ru.pakarpichev.weatherapp.presentation.screens.ForecastScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.pakarpichev.weatherapp.R
import ru.pakarpichev.weatherapp.presentation.ui.WeatherCardModel
import ru.pakarpichev.weatherapp.ui.theme.background
import ru.pakarpichev.weatherapp.ui.theme.card
import ru.pakarpichev.weatherapp.utils.Utils


@Composable
fun ForecastScreen(navController: NavController, cityId: String?) {
    val viewModel = hiltViewModel<ForecastViewModel>()
    val model = viewModel.data.observeAsState().value
    val mContext = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.getForecast(
            cityId ?: "Saint Petersburg",
            language = mContext.getString(R.string.language),
            mContext
        )
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
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                items(model.list) { model ->
                    WeatherCardModel(model = model, id = model.weather[0].icon)
                }
            }
        }
    }
}
