package com.example.composebook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.composebook.restaurant.RestaurantsScreen
import com.example.composebook.restaurantdetails.RestaurantDetailsScreen
import com.example.composebook.ui.theme.ComposeBookTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeBookTheme {
                RestaurantsApp()
            }
        }
    }
}

@Composable
private fun RestaurantsApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "restaurants"
    ) {
        // Add your navigation destinations here
        composable(
            route = "restaurants",
        ) {
            RestaurantsScreen { id ->
                navController.navigate("restaurant_details/$id")
            }
        }
        composable(
            route = "restaurant_details/{restaurant_id}",
            arguments = listOf(navArgument("restaurant_id") {
                type = NavType.IntType
                defaultValue = 0
            })
        ) { navStackEntry ->
            val id = navStackEntry.arguments?.getInt("restaurant_id") ?: "0"
            RestaurantDetailsScreen()
        }
    }
}