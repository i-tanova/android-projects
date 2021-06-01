package com.tanovait.sunnyapp.api

import com.tanovait.sunnyapp.data.ForecastResponse
import com.tanovait.sunnyapp.data.HourlyForecast
import com.tanovait.sunnyapp.data.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CommunityAPIInterface {

    @GET("/weather")
    suspend fun getWeather(@Query("q") city: String, @Query("units") units: String): WeatherResponse

    @GET("/forecast")
    suspend fun getForecast(@Query("q") city: String, @Query("units") units: String): ForecastResponse
}

interface OpenWeatherAPIInterface {

    @GET("/data/2.5/forecast")
    suspend fun getHourlyForecast(@Query("q") city: String, @Query("units") units: String, @Query("appid") appid: String): HourlyForecast
}