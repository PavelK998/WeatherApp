package ru.pakarpichev.weatherapp.presentation.useCase

import ru.pakarpichev.weatherapp.domain.repository.RetrofitRepository
import javax.inject.Inject

class mainUseCase @Inject constructor( val repository: RetrofitRepository) {

    suspend fun getData(
        city: String, apiKey: String, units: String, language: String ) =
        repository.getJsonRequest(city, apiKey, units, language)

    suspend fun getForecast(
        city: String, apiKey: String, units: String, language: String ) =
        repository.getForecast(city, apiKey, units, language)

}