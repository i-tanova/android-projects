package com.tanovait.sunnyapp.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tanovait.sunnyapp.api.APIClient
import com.tanovait.sunnyapp.api.APIInterface
import com.tanovait.sunnyapp.data.ForecastResponse
import com.tanovait.sunnyapp.data.Weather
import com.tanovait.sunnyapp.data.WeatherResponse
import com.tanovait.sunnyapp.data.WeatherUI
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

class WeatherViewModel : ViewModel() {

    val api = APIClient.client?.create(APIInterface::class.java)
    val coroutineScope = CoroutineScope(Dispatchers.Main)
    val weatherLiveData = MutableLiveData<WeatherResponse>()
    val forecastLiveData = MutableLiveData<ForecastResponse>()


    init {
        Log.i("WeatherViewModel", "WeatherViewModel created!")
    }

    fun fetch() {
        coroutineScope.launch(Dispatchers.IO) {
            val weatherResponse = api?.getWeather("Sofia", "metric")
            weatherLiveData.postValue(weatherResponse)
        }

        coroutineScope.launch(Dispatchers.IO) {
            try {
                val forecastResponse = withTimeout<ForecastResponse?>(15000)
                { retry<ForecastResponse?>(3, ::getForecast) }
                forecastLiveData.postValue(forecastResponse)
            } catch (e: Exception) {
                Log.e("TAG", e.localizedMessage, e)
            }
        }
    }

    suspend fun <T> retry(numOfRetries: Int, block: suspend () -> T): T {
        var delayMs = 1000L
        repeat(numOfRetries) {
            try {
                return block()
            } catch (e: java.lang.Exception) {
                Log.e("TAG", e.localizedMessage, e)
            }
            delay(delayMs)
            delayMs *= 2
        }

        return block()
    }

    private suspend fun getForecast() = api?.getForecast("Sofia", "metric")

    override fun onCleared() {
        super.onCleared()
        Log.i("WeatherViewModel", "WeatherViewModel destroyed!")
    }
}