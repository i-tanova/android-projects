package com.tanovait.sunnyapp.ui.main

import com.tanovait.sunnyapp.data.ForecastResponse
import com.tanovait.sunnyapp.data.WeatherResponse

class WeatherRepository(val weatherDataSource: WeatherDataSource) {

    suspend fun getWeather(city: String, metrics: String): WeatherResponse? {
        return weatherDataSource.getWeather(city, metrics)
    }

    suspend fun getForecast(): ForecastResponse? {
        return weatherDataSource.getForecast()
    }
}