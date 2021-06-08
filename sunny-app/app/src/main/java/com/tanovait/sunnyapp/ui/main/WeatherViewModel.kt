package com.tanovait.sunnyapp.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanovait.sunnyapp.api.APIClient
import com.tanovait.sunnyapp.api.CommunityAPIInterface
import com.tanovait.sunnyapp.data.ForecastResponse
import com.tanovait.sunnyapp.data.WeatherResponse
import kotlinx.coroutines.*

class WeatherViewModel : ViewModel() {

    val tag = "WeatherViewModel"

    val communityApi = APIClient.comunityClient?.create(CommunityAPIInterface::class.java)
    val weatherLiveData = MutableLiveData<WeatherResponse>()
    val forecastLiveData = MutableLiveData<ForecastResponse>()

    init {
        Log.i("WeatherViewModel", "WeatherViewModel created!")
        fetch()
    }

    private fun fetch() {
        viewModelScope.launch(Dispatchers.IO) {
            val weatherResponse = communityApi?.getWeather("Sofia", "metric")
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

    private suspend fun getForecast() = communityApi?.getForecast("Sofia", "metric")

    override fun onCleared() {
        super.onCleared()
        Log.i(tag, "WeatherViewModel destroyed!")
    }
}