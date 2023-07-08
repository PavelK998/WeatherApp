package ru.pakarpichev.weatherapp.presentation.screens.SplashScreen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.pakarpichev.weatherapp.R
import kotlinx.coroutines.delay
import ru.pakarpichev.weatherapp.presentation.navigation.Screens
import ru.pakarpichev.weatherapp.ui.theme.background
import ru.pakarpichev.weatherapp.ui.theme.card

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SplashScreen(navController: NavController) {
    val viewModel = hiltViewModel<SplashViewModel>()
    val mContext = LocalContext.current
    viewModel.checkInternet(mContext)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (viewModel.checkInternet(mContext)) {

            LaunchedEffect(key1 = true) {

                delay(1000)
                navController.navigate(Screens.MainScreen.route) {
                    popUpTo(Screens.SplashScreen.route) {
                        inclusive = true
                    }
                }
            }
        } else {
            Toast.makeText(mContext, stringResource(id = R.string.connectivity), Toast.LENGTH_SHORT)
                .show()
        }

        Box(
            modifier = Modifier
                .fillMaxHeight(0.5f),
            contentAlignment = Alignment.BottomCenter
        ) {
            Image(painter = painterResource(id = R.drawable.splash_svg), contentDescription = "")
        }
        Box(
            modifier = Modifier.fillMaxHeight(),
            contentAlignment = Alignment.BottomCenter
        ) {
            if (viewModel.checkInternet(mContext)) {
                CircularProgressIndicator(modifier = Modifier.padding(bottom = 80.dp))
            } else {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(40.dp)
                        .clickable {
                            viewModel.checkInternet(mContext)
                        },
                    backgroundColor = card
                ) {
                    Text(
                        modifier = Modifier
                            .padding(15.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = stringResource(id = R.string.try_again)
                    )
                }
            }
        }
    }
}

