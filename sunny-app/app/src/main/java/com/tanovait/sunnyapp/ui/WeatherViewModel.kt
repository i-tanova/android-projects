package com.tanovait.sunnyapp.ui

import android.util.Log
import androidx.lifecycle.ViewModel

class WeatherViewModel: ViewModel() {

    init {
        Log.i("GameViewModel", "GameViewModel created!")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "GameViewModel destroyed!")
    }
}