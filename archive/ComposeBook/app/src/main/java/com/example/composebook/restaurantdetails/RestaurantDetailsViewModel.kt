package com.example.composebook.restaurantdetails

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composebook.RestaurantsApiService
import com.example.composebook.data.Restaurant
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestaurantDetailsViewModel(private val savedState: SavedStateHandle) : ViewModel() {

    private var restInterface: RestaurantsApiService
    private val _restaurantDetails = mutableStateOf<Restaurant?>(null)
    val restaurantDetails: State<Restaurant?> = _restaurantDetails

    init {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://restaurants-compose-60685-default-rtdb.europe-west1.firebasedatabase.app/")
            .build()
        restInterface = retrofit.create(RestaurantsApiService::class.java)

        val id = savedState.get<Int>("restaurant_id") ?: 0
        getRestaurantDetails(id)
    }

    private fun getRestaurantDetails(id: Int) {
        viewModelScope.launch {
            try {
                val responseMap = restInterface.getRestaurantDetails(id)
                val resturant = responseMap.values.firstOrNull()
                Log.d("RestaurantDetailsVi", "Getting restaurant details $resturant")
                _restaurantDetails.value = resturant
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


}