package com.tanovait.sunnyapp.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanovait.sunnyapp.BuildConfig
import com.tanovait.sunnyapp.api.APIClient
import com.tanovait.sunnyapp.api.OpenWeatherAPIInterface
import com.tanovait.sunnyapp.data.Failure
import com.tanovait.sunnyapp.data.List1
import com.tanovait.sunnyapp.data.OneTimeEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class WeatherDetailViewModel : ViewModel() {
    val tag = "WeatherDetailViewModel"

    val openWeatherApi = APIClient.openMapClient?.create(OpenWeatherAPIInterface::class.java)

    val forecastLiveData = MutableLiveData<WeatherListUI>()

    // Day - 1622764800000

    fun fetch(dayTime: String) {
        Log.d(tag, "fetch()")
        forecastLiveData.postValue(WeatherListUI(isLoading = true))
        viewModelScope.launch(Dispatchers.IO) {

            val hourlyForecastResponse = openWeatherApi?.getHourlyForecast("Sofia", "metric", BuildConfig.HOURLY_APP_KEY)

            val d  = Date(hourlyForecastResponse?.list!!.get(0).dt * 1000)
            val format = SimpleDateFormat("dd.MM EEEE")
            Log.d(tag, format.format(d))

            val result = hourlyForecastResponse?.list?.filter { filterDT(it, dayTime) }

            val weatherUIList = result?.map { WeatherUI(it.dt.toLong(), it.dt_txt, com.tanovait.sunnyapp.ui.IMAGE.CLOUDS) }
            weatherUIList?.let {
                forecastLiveData.postValue(WeatherListUI(false, weatherUIList))
            }
                    ?: forecastLiveData.postValue(WeatherListUI(isLoading = false, error = OneTimeEvent(Failure.CustomFailure())))
        }
    }

    private fun filterDT(it: List1, dayTime: String): Boolean{
        Log.d(tag, it.dt.toString())
        Log.d(tag, dayTime)
        return (it.dt * 1000).toString() == dayTime
    }

}