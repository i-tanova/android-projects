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
    val format = SimpleDateFormat("dd.MM EEEE hh:mm:ss")

    fun fetch(dayTime: String) {
        Log.d(tag, "fetch()")
        forecastLiveData.postValue(WeatherListUI(isLoading = true))
        viewModelScope.launch(Dispatchers.IO) {

            val hourlyForecastResponse = openWeatherApi?.getHourlyForecast("Sofia", "metric", BuildConfig.HOURLY_APP_KEY)
//            hourlyForecastResponse?.list?.forEach {
//                val d = Date(it.dt * 1000)
//                Log.d(tag, "Result: ${format.format(d)}")
//            }
//
//            Log.d(tag, "Filter by: " + format.format(Date(dayTime.toLong())))
//
//            val result = hourlyForecastResponse?.list?.filter { filterDT(it, dayTime) }
//            result?.forEach {
//                val d = Date(it.dt * 1000)
//                Log.d(tag, "Filter: ${format.format(d)}")
//            }

            val d = Date(hourlyForecastResponse?.list!!.get(0).dt * 1000)
            val format = SimpleDateFormat("dd.MM EEEE")
            Log.d(tag, format.format(d))

            val result = hourlyForecastResponse?.list?.filter { filterDT(it, dayTime) }

            val weatherUIList = result?.map { WeatherUI(it.dt.toLong(), it.dt_txt, com.tanovait.sunnyapp.ui.IMAGE.CLOUDS) }
            weatherUIList.let {
                forecastLiveData.postValue(WeatherListUI(false, weatherUIList))
            }
        }
    }

    private fun filterDT(it: List1, dayTime: String): Boolean {
        val format = SimpleDateFormat("dd.MM")
        val value1 = format.format(Date(it.dt * 1000))
        val value2 = format.format(Date(dayTime.toLong()))
        Log.d(tag, "Compare")
        Log.d(tag, value1)
        Log.d(tag, value2)
        return  value1 == value2
    }

}