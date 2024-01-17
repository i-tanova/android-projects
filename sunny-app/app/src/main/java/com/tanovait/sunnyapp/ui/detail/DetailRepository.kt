package com.tanovait.sunnyapp.ui.detail

import com.tanovait.sunnyapp.data.HourlyForecast

class DetailsRepository(val dataSource: DetailsDataSource) {
    suspend fun getHourlyForecast(city: String, metrics: String): HourlyForecast? {
        return dataSource.getHourlyForecast(city, metrics)
    }
}
