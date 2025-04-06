package com.example.composebook.restaurant

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composebook.RestaurantsApiService
import com.example.composebook.data.Restaurant
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Collections.emptyList

class RestaurantsViewModel : ViewModel() {

    private val _restaurants = MutableStateFlow(emptyList<Restaurant>())
    val restaurants: StateFlow<List<Restaurant>> = _restaurants
    private var restInterface: RestaurantsApiService

    private val errorHandler = CoroutineExceptionHandler { _, e ->
        Log.e("RestaurantsViewModel", "Error ${e.printStackTrace()}", e)
    }

    init {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://restaurants-compose-60685-default-rtdb.europe-west1.firebasedatabase.app/")
            .build()
        restInterface = retrofit.create(RestaurantsApiService::class.java)

        getRestaurants()
    }

    private fun getRestaurants() {
        viewModelScope.launch(context = Dispatchers.Main + errorHandler) {
            try{
                //withContext(Dispatchers.IO) { Retrofit is already doing this in the background
                    Log.d("RestaurantsViewModel", "Getting restaurants ${Thread.currentThread().name}")
                    _restaurants.value = restInterface.getRestaurants()
              //  }
            } catch (e: Exception) {
                _restaurants.value = emptyList()
                Log.e("RestaurantsViewModel", "Error getting restaurants ${e.printStackTrace()}", e)
            }
        }
    }

    fun toggleFavorite(restaurant: Restaurant) {
        viewModelScope.launch {
            _restaurants.value = _restaurants.value.map {
                if (it.id == restaurant.id) {
                    it.copy(isFavourite = !it.isFavourite)
                } else {
                    it
                }
            }
        }
    }
}