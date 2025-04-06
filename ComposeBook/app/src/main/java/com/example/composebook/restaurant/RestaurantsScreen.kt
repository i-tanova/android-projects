package com.example.composebook.restaurant

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composebook.ui.theme.ComposeBookTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composebook.data.Restaurant

@Composable
fun RestaurantsScreen(onItemClick: (id: Int) -> Unit = {}) {

    val viewModel: RestaurantsViewModel = viewModel()
    val restaurants by viewModel.restaurants.collectAsState()

    LazyColumn(contentPadding = PaddingValues(vertical = 8.dp, horizontal = 8.dp)) {
        items(restaurants.size) { index ->
            RestaurantItem(restaurants[index],
                onItemClick = onItemClick,
                onFavoriteSelected = { viewModel.toggleFavorite(restaurants[index]) })
        }
    }
}

@Composable
fun RestaurantItem(
    restaurant: Restaurant,
    onFavoriteSelected: () -> Unit,
    onItemClick: (id: Int) -> Unit = {}
) {
    Card(modifier = Modifier
        .padding(8.dp)
        .clickable { onItemClick(restaurant.id) }) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
            RestaurantIcon(Icons.Filled.Place, Modifier.weight(0.15f))
            RestaurantDetails(Modifier.weight(0.7f), restaurant.name, restaurant.description)
            FavoriteIcon(
                Modifier.weight(0.15f),
                restaurant.isFavourite,
                onSelected = onFavoriteSelected
            )
        }
    }
}

@Composable
fun FavoriteIcon(modifier: Modifier, isSelected: Boolean, onSelected: () -> Unit) {
    Image(
        imageVector = if (isSelected) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
        contentDescription = "Restaurant Icon",
        modifier = modifier
            .padding(8.dp)
            .clickable {
                onSelected()
            }
    )
}

@Composable
fun RestaurantDetails(
    modifier: Modifier,
    title: String,
    description: String,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start
) {
    Column(modifier = modifier, horizontalAlignment = horizontalAlignment) {
        Text(text = title, style = MaterialTheme.typography.headlineMedium)
        CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.primary) {
            Text(text = description, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun RestaurantIcon(place: ImageVector, modifier: Modifier) {
    Image(
        imageVector = place,
        contentDescription = "Restaurant Icon",
        modifier = modifier.padding(8.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeBookTheme {
        RestaurantsScreen()
    }
}
