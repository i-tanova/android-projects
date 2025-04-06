package com.example.composebook.restaurantdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composebook.data.Restaurant
import com.example.composebook.restaurant.RestaurantDetails
import com.example.composebook.restaurant.RestaurantIcon

@Composable
fun RestaurantDetailsScreen(
) {
   val viewModel: RestaurantDetailsViewModel = viewModel()
    val item = viewModel.restaurantDetails.value
    if(item != null){
        RestaurantDetailsContent(
            restaurant = item,
        )
    } else {
        // Handle loading state
    }
}

@Composable
fun RestaurantDetailsContent(
    restaurant: Restaurant,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RestaurantIcon(
            Icons.Filled.Place,
            modifier = Modifier.padding(top = 32.dp, bottom = 32.dp)
        )
        RestaurantDetails(
            modifier = Modifier.padding(bottom = 32.dp),
            restaurant.name,
            description = restaurant.description,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        Text("More info coming soon...",)
//        FavoriteIcon(
//            isSelected = restaurant.isFavourite,
//            onSelected = onToggleFavourite,
//            modifier = Modifier.padding(8.dp)
//        )

    }
}

@Preview
@Composable
fun RestaurantDetailsScreenPreview() {
    RestaurantDetailsContent(
        restaurant = Restaurant(1, "Restaurant Name", "Restaurant Description"),
    )
}
