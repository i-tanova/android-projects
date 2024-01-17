package com.tanovait.sunnyapp.api

import com.tanovait.sunnyapp.data.ForecastResponse
import com.tanovait.sunnyapp.data.HourlyForecast
import com.tanovait.sunnyapp.data.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherAPIInterface {

    @GET("/data/2.5//weather")
    suspend fun getWeather(@Query("q") city: String, @Query("units") units: String, @Query("appid") appid: String): WeatherResponse

    @GET("/data/2.5/forecast")
    suspend fun getForecast(@Query("q") city: String, @Query("units") units: String, @Query("appid") appid: String): ForecastResponse

    @GET("/data/2.5/forecast")
    suspend fun getHourlyForecast(@Query("q") city: String, @Query("units") units: String, @Query("appid") appid: String): HourlyForecast
}