package com.example.composebook

import com.example.composebook.data.Restaurant
import retrofit2.http.GET
import retrofit2.http.Query


interface RestaurantsApiService {

    @GET("restaurants.json")
    suspend fun getRestaurants(): List<Restaurant>

    @GET("restaurants.json?orderBy=%22r_id%22")
    suspend fun getRestaurantDetails(@Query("equalTo") id: Int): Map<String, Restaurant>
}