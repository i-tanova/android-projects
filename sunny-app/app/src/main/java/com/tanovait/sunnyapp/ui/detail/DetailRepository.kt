package com.tanovait.sunnyapp.ui.detail

import com.tanovait.sunnyapp.BuildConfig
import com.tanovait.sunnyapp.api.APIClient
import com.tanovait.sunnyapp.api.OpenWeatherAPIInterface
import com.tanovait.sunnyapp.data.HourlyForecast

class DetailsRepository(val dataSource: DetailsDataSource) {

    suspend fun getHourlyForecast(city: String, metrics: String): HourlyForecast?{
        return dataSource.getHourlyForecast(city, metrics)
    }
}

interface DetailsDataSource {
    suspend fun getHourlyForecast(city: String, metrics: String): HourlyForecast?
}

class DetailsDataSourceImpl : DetailsDataSource {
    val openWeatherApi = APIClient.openMapClient?.create(OpenWeatherAPIInterface::class.java)

    override suspend fun getHourlyForecast(city: String, metrics: String): HourlyForecast? {
        return openWeatherApi?.getHourlyForecast("Amsterdam", "metric", BuildConfig.HOURLY_APP_KEY)
    }
}