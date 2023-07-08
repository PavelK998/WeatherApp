package ru.pakarpichev.weatherapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.pakarpichev.weatherapp.presentation.screens.DetailsScreen.DetailsScreen
import ru.pakarpichev.weatherapp.presentation.screens.ForecastScreen.ForecastScreen
import ru.pakarpichev.weatherapp.presentation.screens.MainScreen.MainScreen
import ru.pakarpichev.weatherapp.presentation.screens.SplashScreen.SplashScreen


sealed class Screens (val route: String){
    object SplashScreen: Screens (route = "splash_screen")
    object MainScreen: Screens(route = "main_screen")
    object DetailsScreen: Screens(route = "details_screen")
    object ForecastScreen: Screens(route = "forecast_screen")
}

@Composable
fun SetupNavHost(navController: NavHostController) {

    NavHost(navController = navController, startDestination = Screens.SplashScreen.route ){

        composable(route = Screens.MainScreen.route){
            MainScreen(navController)
        }
        composable(route = Screens.SplashScreen.route){
            SplashScreen(navController)
        }
        composable(route = Screens.DetailsScreen.route + "/{cityId}", arguments = listOf(navArgument("cityId"){type = NavType.StringType})){
            DetailsScreen(navController, it.arguments?.getString("cityId"))
        }
        composable(route = Screens.ForecastScreen.route+ "/{cityId}", arguments = listOf(navArgument("cityId"){type = NavType.StringType})){
            ForecastScreen(navController, it.arguments?.getString("cityId"))
        }
    }
}
