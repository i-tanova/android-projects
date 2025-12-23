package com.example.composebook.data

import com.google.gson.annotations.SerializedName

data class Restaurant(
    @SerializedName("r_id") val id: Int,
    @SerializedName("r_title") val name: String,
    @SerializedName("r_description") val description: String,
    val isFavourite: Boolean = false
)