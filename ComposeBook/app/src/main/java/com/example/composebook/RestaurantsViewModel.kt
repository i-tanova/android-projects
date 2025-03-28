package com.example.composebook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composebook.data.Restaurant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Collections.emptyList

class RestaurantsViewModel : ViewModel() {

    private val _restaurants = MutableStateFlow(emptyList<Restaurant>())
    val restaurants: StateFlow<List<Restaurant>> = _restaurants
    private var restInterface: RestaurantsApiService

    init {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://restaurants-compose-60685-default-rtdb.europe-west1.firebasedatabase.app/")
            .build()
        restInterface = retrofit.create(RestaurantsApiService::class.java)
    }

    fun getRestaurants() {
        viewModelScope.launch {
            restInterface.getRestaurants().enqueue(object : Callback<List<Restaurant>>{
                override fun onResponse(call: Call<List<Restaurant>>, response: Response<List<Restaurant>>) {
                    if (response.isSuccessful) {
                        _restaurants.value = response.body() ?: emptyList()
                    }
                }

                override fun onFailure(call: Call<List<Restaurant>>, t: Throwable) {
                    // Handle failure
                    t.printStackTrace()
                }
            })
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