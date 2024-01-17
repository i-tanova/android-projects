package com.tanovait.sunnyapp.ui.main

import com.tanovait.sunnyapp.BuildConfig
import com.tanovait.sunnyapp.api.APIClient
import com.tanovait.sunnyapp.api.OpenWeatherAPIInterface
import com.tanovait.sunnyapp.data.ForecastResponse
import com.tanovait.sunnyapp.data.WeatherResponse

interface WeatherDataSource {
    suspend fun getWeather(city: String, metrics: String): WeatherResponse?
    suspend fun getForecast(): ForecastResponse?
}

class WeatherDataSourceImpl : WeatherDataSource {

    private val communityApi = APIClient.openMapClient?.create(OpenWeatherAPIInterface::class.java)

    override suspend fun getWeather(city: String, metrics: String): WeatherResponse? {
        return communityApi?.getWeather("Amsterdam", "metric", BuildConfig.APP_KEY)
    }

    override suspend fun getForecast(): ForecastResponse? {
        return communityApi?.getForecast("Amsterdam", "metric", BuildConfig.APP_KEY)
    }
}