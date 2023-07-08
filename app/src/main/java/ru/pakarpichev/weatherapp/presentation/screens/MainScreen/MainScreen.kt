package ru.pakarpichev.weatherapp.presentation.screens.MainScreen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
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
fun MainScreen(navController: NavController) {
    val mContext = LocalContext.current
    val viewModel = hiltViewModel<MainViewModel>()
    val isInputValid = viewModel.isInputValid.collectAsState().value
    val model = viewModel.data.observeAsState().value
    var userText by rememberSaveable {
        mutableStateOf(mContext.getString(R.string.standard_displaying_city))
    }
    var icon by remember {
        mutableStateOf(1)
    }
    var isOpen by remember {
        mutableStateOf(false)
    }
    if (model == null) {
        LaunchedEffect(key1 = true) {
            viewModel.getData(userText, mContext.getString(R.string.language), mContext)
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(backgroundColor = card) {
            }
        },
        backgroundColor = background
    ) {
        if (model == null) {
            Utils.Loading()
        } else {
            if (isInputValid == "Not valid") {
                userText = model.name
            }
            if (isOpen) {
                AlertDialog(
                    backgroundColor = card,
                    onDismissRequest = { isOpen = false },
                    title = {
                        Text(
                            modifier = Modifier.padding(5.dp),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            text = stringResource(id = R.string.choose_city)
                        )
                    },
                    text = {
                        OutlinedTextField(
                            textStyle = TextStyle.Default.copy(fontSize = 16.sp),
                            modifier = Modifier.padding(top = 5.dp),
                            value = userText,
                            onValueChange = { userText = it })
                    },
                    buttons = {
                        Button(
                            colors = ButtonDefaults.buttonColors(backgroundColor = card),
                            onClick = {
                                if (viewModel.checkInternet(mContext)) {
                                    if (userText.isNotBlank()) {
                                        viewModel.getData(
                                            city = userText,
                                            mContext.getString(R.string.language),
                                            mContext
                                        )
                                        isOpen = false
                                    } else {
                                        Toast.makeText(
                                            mContext,
                                            mContext.getString(R.string.type_something),
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                } else {
                                    Toast.makeText(
                                        mContext,
                                        mContext.getString(R.string.connectivity),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                            }) {
                            Text(
                                modifier = Modifier.padding(4.dp),
                                text = "OK"
                            )
                        }
                    }
                )
            }
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
                    shape = RoundedCornerShape(8.dp),
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

                                    .padding(start = 12.dp, top = 12.dp),
                                fontSize = 22.sp,
                                text = model.name,
                            )
                            Spacer(modifier = Modifier.weight(1f, true))
                            Icon(
                                modifier = Modifier
                                    .clickable {
                                        isOpen = !isOpen
                                    }
                                    .padding(10.dp),
                                painter = painterResource(id = R.drawable.search),
                                contentDescription = ""
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
                                        .height(50.dp)
                                        .width(50.dp),
                                    painter = painterResource(id = icon), contentDescription = ""
                                )
                            }
                            Text(
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Bold,
                                text = "${model.main.temp.roundToInt()}°C",
                            )
                        }
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
                                text = model.main.pressure + " " + stringResource(id = R.string.pressure)
                            )
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
                when (model.weather[0].icon) {
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
                Row(modifier = Modifier.fillMaxWidth()) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .padding(10.dp)
                            .clickable {
                                viewModel.checkInternet(mContext)
                                if (viewModel.checkInternet(mContext)) {
                                    navController.navigate(Screens.DetailsScreen.route + "/${userText}")
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
                            text = stringResource(id = R.string.details)
                        )
                    }
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .clickable {
                                viewModel.checkInternet(mContext)
                                if (viewModel.checkInternet(mContext)) {
                                    navController.navigate(Screens.ForecastScreen.route + "/${userText}")
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
}


    

