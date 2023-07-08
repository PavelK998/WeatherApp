package ru.pakarpichev.weatherapp.presentation.screens.ForecastScreen

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.pakarpichev.weatherapp.R
import ru.pakarpichev.weatherapp.domain.model.ForecastFiveDaysModel
import ru.pakarpichev.weatherapp.presentation.useCase.mainUseCase
import ru.pakarpichev.weatherapp.utils.Constants
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(private val useCase: mainUseCase) : ViewModel() {

    private val _data = MutableLiveData<ForecastFiveDaysModel>()
    val data: LiveData<ForecastFiveDaysModel>
        get() = _data

    fun getForecast(city: String, language: String, context: Context) {
        viewModelScope.launch {
            try {
                useCase.getForecast(
                    city, apiKey = Constants.API_KEY, units = Constants.UNITS, language = language
                ).let {
                    _data.postValue(it)
                }
            } catch (e: Exception) {
                Log.d(Constants.EXCEPTION, "Forecast View Model: ${e.localizedMessage} ")
                Toast.makeText(
                    context,
                    context.getString(R.string.something_wrong),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}