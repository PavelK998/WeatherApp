package ru.pakarpichev.weatherapp.presentation.screens.SplashScreen

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.pakarpichev.weatherapp.presentation.useCase.AppStatusUseCase
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val useCase: AppStatusUseCase): ViewModel() {

    fun checkInternet(context: Context):Boolean{
        val result = mutableStateOf(false)
        useCase.checkInternetStatus(context).let {
            result.value = it
        }
        return result.value
    }
}