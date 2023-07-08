package ru.pakarpichev.weatherapp.domain.repository

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.pakarpichev.weatherapp.domain.model.ForecastFiveDaysModel
import ru.pakarpichev.weatherapp.domain.model.WeatherModel
import ru.pakarpichev.weatherapp.utils.Constants

interface RetrofitRepository {
    @GET("weather")
    suspend fun getJsonRequest(
        @Query("q") city:String,
        @Query("appid") apiKey: String,
        @Query("units") units: String,
        @Query("lang") language: String
    ): WeatherModel

    @GET("forecast")
    suspend fun getForecast (
        @Query("q") city:String,
        @Query("appid") apiKey: String,
        @Query("units") units: String,
        @Query("lang") language: String
    ): ForecastFiveDaysModel
}