package com.example.composebook.restaurantdetails

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composebook.RestaurantsApiService
import com.example.composebook.data.Restaurant
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.logging.Logger

class RestaurantDetailsViewModel : ViewModel() {

    private var restInterface: RestaurantsApiService
    private val _restaurantDetails = mutableStateOf<Restaurant?>(null)
    val restaurantDetails: State<Restaurant?> = _restaurantDetails

    init {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://restaurants-compose-60685-default-rtdb.europe-west1.firebasedatabase.app/")
            .build()
        restInterface = retrofit.create(RestaurantsApiService::class.java)

        getRestaurantDetails()
    }

    private fun getRestaurantDetails() {
        viewModelScope.launch {
            try {
                val responseMap = restInterface.getRestaurantDetails(2    )
                val resturant = responseMap.values.firstOrNull()
                Log.d("RestaurantDetailsVi",  "Getting restaurant details $resturant")
                _restaurantDetails.value = resturant
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


}