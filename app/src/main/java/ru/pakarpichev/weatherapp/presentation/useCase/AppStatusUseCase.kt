package ru.pakarpichev.weatherapp.presentation.useCase

import android.content.Context
import ru.pakarpichev.weatherapp.domain.repository.AppStatus
import javax.inject.Inject

class AppStatusUseCase @Inject constructor(private val repository: AppStatus) {

    fun checkInternetStatus (context: Context) = repository.checkInternet(context)
}