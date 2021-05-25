package com.tanovait.sunnyapp.data

import com.tanovait.sunnyapp.ui.MainActivity2

data class WeatherUI(val day: String, val icon: MainActivity2.IMAGE)

data class WeatherListUI(
        val isLoading: Boolean = false,
        val weatherByDays: List<WeatherUI> = emptyList(),
        val error: OneTimeEvent<Failure>? = null
)