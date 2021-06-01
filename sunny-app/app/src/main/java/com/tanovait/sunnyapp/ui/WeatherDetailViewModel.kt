package com.tanovait.sunnyapp.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanovait.sunnyapp.BuildConfig
import com.tanovait.sunnyapp.api.APIClient
import com.tanovait.sunnyapp.api.OpenWeatherAPIInterface
import com.tanovait.sunnyapp.data.HourlyForecast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherDetailViewModel : ViewModel() {
    val tag = "WeatherDetailViewModel"

    val openWeatherApi = APIClient.openMapClient?.create(OpenWeatherAPIInterface::class.java)

    val forecastLiveData = MutableLiveData<HourlyForecast>()

    fun fetch() {
        Log.d(tag, "fetch()")
        viewModelScope.launch(Dispatchers.IO) {
            val hourlyForecastResponse = openWeatherApi?.getHourlyForecast("Sofia", "metric", BuildConfig.HOURLY_APP_KEY)
            forecastLiveData.postValue(hourlyForecastResponse)

        }
    }
}