package ru.pakarpichev.weatherapp.domain.model

data class ForecastFiveDaysModel(
    val list: List<ForecastModel>
)
data class ForecastModel(
    val main: MainClass,
    val weather: List<WeatherClass>,
    val wind: WindClass,
    val dt_txt: String
)

data class MainClass(
    val temp: Float,
    val pressure: String,
    val humidity: String,
    val feels_like: Float,
    val temp_min: Float,
    val temp_max: Float
)

data class WeatherClass(
    val id: String,
    val main: String,
    val description: String,
    val icon: String,
)

data class WindClass(
    val speed: String
)