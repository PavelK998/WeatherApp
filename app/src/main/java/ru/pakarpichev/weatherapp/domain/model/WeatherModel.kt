package ru.pakarpichev.weatherapp.domain.model

data class WeatherModel(
    val name: String,
    val main: Main,
    val wind: Wind,
    val weather: List<Weather>,
    val visibility: Int,
)

data class Main (
    val temp: Float,
    val pressure: String,
    val humidity: String,
    val feels_like: Float,
    val temp_min: Float,
    val temp_max: Float
)

data class Wind (
    val speed: String
)

data class Weather (
    val id: String,
    val main: String,
    val description: String,
    val icon: String,
)

