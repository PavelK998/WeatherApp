package ru.pakarpichev.weatherapp.presentation.screens.MainScreen

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.pakarpichev.weatherapp.R
import ru.pakarpichev.weatherapp.domain.model.WeatherModel
import ru.pakarpichev.weatherapp.presentation.useCase.AppStatusUseCase
import ru.pakarpichev.weatherapp.presentation.useCase.mainUseCase
import ru.pakarpichev.weatherapp.utils.Constants
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: mainUseCase, private val statusUseCase: AppStatusUseCase
) : ViewModel() {


    private val _data = MutableLiveData<WeatherModel>()
    val data: LiveData<WeatherModel>
        get() = _data

    private val _isInputValid = MutableStateFlow("")
    val isInputValid = _isInputValid.asStateFlow()

    fun getData(city: String, language: String, context: Context) {
        viewModelScope.launch {
            try {
                _isInputValid.value = ""
                useCase.getData(
                    city, apiKey = Constants.API_KEY, units = Constants.UNITS, language = language
                ).let {
                    _data.postValue(it)
                }
            } catch (e: Exception) {
                Log.d(Constants.EXCEPTION, "Main View Model: ${e.localizedMessage} ")
                if (e.localizedMessage == "HTTP 404 Not Found") {
                    _isInputValid.value = "Not valid"
                    Toast.makeText(
                        context,
                        context.getString(R.string.wrong_city),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        context,
                        context.getString(R.string.something_wrong),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }


    fun checkInternet(context: Context): Boolean {
        val result = mutableStateOf(false)
        statusUseCase.checkInternetStatus(context).let {
            result.value = it
        }
        return result.value
    }

}