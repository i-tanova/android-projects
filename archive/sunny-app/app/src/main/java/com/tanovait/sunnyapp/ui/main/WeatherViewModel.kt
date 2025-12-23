package com.tanovait.sunnyapp.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.tanovait.sunnyapp.data.ForecastResponse
import com.tanovait.sunnyapp.data.WeatherResponse
import kotlinx.coroutines.*

class WeatherViewModel(val repository: WeatherRepository) : ViewModel() {

    val tag = "WeatherViewModel"

    val weatherLiveData = MutableLiveData<WeatherResponse>()
    val forecastLiveData = MutableLiveData<ForecastResponse>()

    init {
        Log.i("WeatherViewModel", "WeatherViewModel created!")
        fetch()
    }

    private fun fetch() {
        viewModelScope.launch(Dispatchers.IO) {
            val weatherResponse = repository.getWeather("Amsterdam", "imperial")
            weatherLiveData.postValue(weatherResponse)
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val forecastResponse = withTimeout<ForecastResponse?>(15000)
                { retry<ForecastResponse?>(3, ::getForecast) }
                forecastLiveData.postValue(forecastResponse)
            } catch (e: Exception) {
                Log.e(tag, e.localizedMessage, e)
            }
        }
    }

    private suspend fun getForecast() = repository.getForecast()

    suspend fun <T> retry(numOfRetries: Int, block: suspend () -> T): T {
        var delayMs = 1000L
        repeat(numOfRetries) {
            try {
                return block()
            } catch (e: java.lang.Exception) {
                Log.e(tag, e.localizedMessage, e)
            }
            delay(delayMs)
            delayMs *= 2
        }

        return block()
    }


    override fun onCleared() {
        super.onCleared()
        Log.i(tag, "WeatherViewModel destroyed!")
    }
}


class WeatherViewModelFactory(private val repository: WeatherRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            return WeatherViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}