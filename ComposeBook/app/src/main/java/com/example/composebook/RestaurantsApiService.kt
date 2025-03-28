package com.example.composebook

import com.example.composebook.data.Restaurant
import retrofit2.Call
import retrofit2.http.GET


interface RestaurantsApiService {

    @GET("restaurants.json")
    fun getRestaurants(): Call<List<Restaurant>>
}