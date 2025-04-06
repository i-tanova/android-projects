package com.example.composebook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.composebook.restaurant.RestaurantsScreen
import com.example.composebook.restaurantdetails.RestaurantDetailsScreen
import com.example.composebook.ui.theme.ComposeBookTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeBookTheme {
                RestaurantDetailsScreen()
               //RestaurantsScreen()
            }
        }
    }
}