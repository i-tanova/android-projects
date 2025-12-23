package com.tanovait.sunnyapp.ui.detail

import com.tanovait.sunnyapp.BuildConfig
import com.tanovait.sunnyapp.api.APIClient
import com.tanovait.sunnyapp.api.OpenWeatherAPIInterface
import com.tanovait.sunnyapp.data.HourlyForecast

interface DetailsDataSource {
    suspend fun getHourlyForecast(city: String, metrics: String): HourlyForecast?
}

class DetailsDataSourceImpl : DetailsDataSource {
    // Get API key from https://home.openweathermap.org/api_keys
    private val openWeatherApi = APIClient.openMapClient?.create(OpenWeatherAPIInterface::class.java)

    override suspend fun getHourlyForecast(city: String, metrics: String): HourlyForecast? {
        return openWeatherApi?.getHourlyForecast("Amsterdam", "metric", BuildConfig.APP_KEY)
    }
}