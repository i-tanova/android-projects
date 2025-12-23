package com.tanovait.sunnyapp.ui.detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.tanovait.sunnyapp.BuildConfig
import com.tanovait.sunnyapp.api.APIClient
import com.tanovait.sunnyapp.api.OpenWeatherAPIInterface
import com.tanovait.sunnyapp.data.List1
import com.tanovait.sunnyapp.data.Weather
import com.tanovait.sunnyapp.data.Weather1
import com.tanovait.sunnyapp.ui.IMAGE
import com.tanovait.sunnyapp.ui.WeatherListUI
import com.tanovait.sunnyapp.ui.WeatherUI
import com.tanovait.sunnyapp.ui.main.WeatherRepository
import com.tanovait.sunnyapp.ui.main.WeatherViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class WeatherDetailViewModel(val repository: DetailsRepository) : ViewModel() {
    val tag = "WeatherDetailViewModel"


    val forecastLiveData = MutableLiveData<WeatherListUI>()

    fun fetch(dayTime: String) {
        Log.d(tag, "fetch()")
        forecastLiveData.postValue(WeatherListUI(isLoading = true))
        viewModelScope.launch(Dispatchers.IO) {

            val hourlyForecastResponse = repository.getHourlyForecast("Amsterdam", "imperial")

            val d = Date(hourlyForecastResponse?.list!!.get(0).dt * 1000)
            val format = SimpleDateFormat("dd.MM EEEE")
            Log.d(tag, format.format(d))

            val result = hourlyForecastResponse?.list?.filter { filterDT(it, dayTime) }

            val weatherUIList = result?.map {
                val weather = getWeatherImageAndDescription(it.weather)
                WeatherUI(it.dt.toLong(), it.dt_txt, weather.first, weather.second)
            }
            weatherUIList.let {
                forecastLiveData.postValue(WeatherListUI(false, weatherUIList))
            }
        }
    }

    private fun filterDT(it: List1, dayTime: String): Boolean {
        val format = SimpleDateFormat("dd.MM")
        val value1 = format.format(Date(it.dt * 1000))
        val value2 = format.format(Date(dayTime.toLong()))
        return value1 == value2
    }

    private fun getWeatherImageAndDescription(weather: List<Weather1>): Pair<String, IMAGE> {
        var imageR = Pair(weather[0].description, IMAGE.CLOUDS)
        var hasRain = false
        var hasClouds = false
        weather.forEach {
            val iconCode = it.icon.dropLast(1)
            if (iconCode == "13") {
                return Pair(it.description, IMAGE.SNOW)
            }

            if (iconCode in listOf<String>("09", "10", "11")) {
                imageR = Pair(it.description, IMAGE.RAIN)
                hasRain = true
            }

            if (iconCode in listOf<String>("02", "03", "04", "50") && !hasRain) {
                imageR = Pair(it.description, IMAGE.CLOUDS)
                hasClouds = true
            }

            if (iconCode == "01" && !hasRain && !hasClouds) {
                imageR = Pair(it.description, IMAGE.SUN)
            }
        }
        return imageR
    }

}

class DetailViewModelFactory(private val repository: DetailsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherDetailViewModel::class.java)) {
            return WeatherDetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}