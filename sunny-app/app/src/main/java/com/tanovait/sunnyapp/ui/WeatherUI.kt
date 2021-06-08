package com.tanovait.sunnyapp.ui

import com.tanovait.sunnyapp.data.Failure
import com.tanovait.sunnyapp.data.OneTimeEvent

enum class IMAGE {
    RAIN, SNOW, SUN, CLOUDS
}

data class WeatherUI(val daytime: Long, val day: String, val description: String, val icon: IMAGE)

data class WeatherListUI(
        val isLoading: Boolean = false,
        val weatherByDays: List<WeatherUI> = emptyList(),
        val error: OneTimeEvent<Failure>? = null
)