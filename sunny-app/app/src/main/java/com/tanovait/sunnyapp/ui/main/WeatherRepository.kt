package com.tanovait.sunnyapp.ui.main

import com.tanovait.sunnyapp.api.APIClient
import com.tanovait.sunnyapp.api.CommunityAPIInterface
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

interface WeatherDataSource {
    suspend fun getWeather(city: String, metrics: String): WeatherResponse?
    suspend fun getForecast(): ForecastResponse?
}

class WeatherDataSourceImpl : WeatherDataSource {

    val communityApi = APIClient.comunityClient?.create(CommunityAPIInterface::class.java)

    override suspend fun getWeather(city: String, metrics: String): WeatherResponse? {
        return communityApi?.getWeather("Sofia", "metric")
    }

    override suspend fun getForecast(): ForecastResponse? {
        return communityApi?.getForecast("Sofia", "metric")
    }
}