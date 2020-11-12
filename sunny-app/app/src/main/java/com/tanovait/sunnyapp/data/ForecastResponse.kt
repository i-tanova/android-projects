package com.tanovait.sunnyapp.data

import com.google.gson.annotations.SerializedName

data class ForecastResponse(
        @SerializedName("cod") val cod : Int,
        @SerializedName("message") val message : Int,
        @SerializedName("cnt") val cnt : Int,
        @SerializedName("list") val list : List<ListForecast>,
        @SerializedName("city") val city : City
)

data class ListForecast(

        @SerializedName("dt") val dt : Long,
        @SerializedName("main") val main : Main,
        @SerializedName("weather") val weather : List<Weather>,
        @SerializedName("clouds") val clouds : Clouds,
        @SerializedName("wind") val wind : Wind,
        @SerializedName("visibility") val visibility : Int,
        @SerializedName("pop") val pop : Double,
        @SerializedName("sys") val sys : Sys,
        @SerializedName("dt_txt") val dt_txt : String
)

data class City (

        @SerializedName("id") val id : Int,
        @SerializedName("name") val name : String,
        @SerializedName("coord") val coord : Coord,
        @SerializedName("country") val country : String,
        @SerializedName("population") val population : Int,
        @SerializedName("timezone") val timezone : Int,
        @SerializedName("sunrise") val sunrise : Int,
        @SerializedName("sunset") val sunset : Int
)