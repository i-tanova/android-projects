package com.tanovait.sunnyapp.data

import com.google.gson.annotations.SerializedName

data class HourlyForecast (

        @SerializedName("cod") val cod : Int,
        @SerializedName("message") val message : Int,
        @SerializedName("cnt") val cnt : Int,
        @SerializedName("list") val list : List<List1>,
        @SerializedName("city") val city : City1
)


data class City1 (

        @SerializedName("id") val id : Int,
        @SerializedName("name") val name : String,
        @SerializedName("coord") val coord : Coord1,
        @SerializedName("country") val country : String,
        @SerializedName("population") val population : Int,
        @SerializedName("timezone") val timezone : Int,
        @SerializedName("sunrise") val sunrise : Int,
        @SerializedName("sunset") val sunset : Int
)

data class Clouds1 (

        @SerializedName("all") val all : Int
)

data class Coord1 (

        @SerializedName("lat") val lat : Double,
        @SerializedName("lon") val lon : Double
)


data class List1 (
        @SerializedName("dt") val dt : Long,
        @SerializedName("main") val main : Main1,
        @SerializedName("weather") val weather : List<Weather1>,
        @SerializedName("clouds") val clouds : Clouds1,
        @SerializedName("wind") val wind : Wind1,
        @SerializedName("visibility") val visibility : Int,
        @SerializedName("pop") val pop : Double,
        @SerializedName("sys") val sys : Sys,
        @SerializedName("dt_txt") val dt_txt : String
)

data class Main1 (

        @SerializedName("temp") val temp : Double,
        @SerializedName("feels_like") val feels_like : Double,
        @SerializedName("temp_min") val temp_min : Double,
        @SerializedName("temp_max") val temp_max : Double,
        @SerializedName("pressure") val pressure : Int,
        @SerializedName("sea_level") val sea_level : Int,
        @SerializedName("grnd_level") val grnd_level : Int,
        @SerializedName("humidity") val humidity : Int,
        @SerializedName("temp_kf") val temp_kf : Double
)

data class Sys1 (

        @SerializedName("pod") val pod : String
)

data class Weather1 (

        @SerializedName("id") val id : Int,
        @SerializedName("main") val main : String,
        @SerializedName("description") val description : String,
        @SerializedName("icon") val icon : String
)


data class Wind1 (

        @SerializedName("speed") val speed : Double,
        @SerializedName("deg") val deg : Int,
        @SerializedName("gust") val gust : Double
)