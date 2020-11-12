package com.tanovait.sunnyapp.ui

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.firstfirestore.MyAdapter
import com.example.firstfirestore.MyViewHolder
import com.tanovait.sunnyapp.R
import com.tanovait.sunnyapp.api.APIClient
import com.tanovait.sunnyapp.api.APIInterface
import com.tanovait.sunnyapp.data.Weather
import com.tanovait.sunnyapp.data.WeatherUI
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

class MainActivity2 : AppCompatActivity() {
    val coroutineScope = CoroutineScope(Dispatchers.Main)
    val api = APIClient.client?.create(APIInterface::class.java)
    val adapter = object : MyAdapter<WeatherUI>() {

        override fun bind(t: WeatherUI, holder: MyViewHolder?) {
            val dayTxtV = holder?.itemView?.findViewById<TextView>(R.id.day_name)
            dayTxtV?.text = t.day

            val dayImageV = holder?.itemView?.findViewById<ImageView>(R.id.day_image)
            dayImageV?.setImageDrawable(getDrawable(imageToDrawble(t.icon)))
        }

        override fun getItemViewType(position: Int): Int {
            return R.layout.forecast_list_item
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        city_text.text = "Sofia"
        weather_forecast_rv.adapter = adapter


        coroutineScope.launch(Dispatchers.IO) {
            try {
                val weatherResponse = api?.getWeather("Sofia", "metric")
                val forecastResponse = api?.getForecast("Sofia", "metric")

                withContext(Dispatchers.Main) {
                    val (description, image) = getWeatherImageAndDescription(weatherResponse!!.weather)
                    today_image.setImageDrawable(getDrawable(imageToDrawble(image)))
                    today_description.text = description
                    today_temperature.text = "${weatherResponse!!.main.temp} C"

                    val weatherList = mutableListOf<WeatherUI>()
                    val dateMapList = mutableListOf<DateWeatherMap>()
                    val dayGroup = forecastResponse!!.list.groupBy { it.dt_txt.split(Pattern.compile("\\s+"))[0] }
                    for ((k, v) in dayGroup) {
                        dateMapList.add(DateWeatherMap(Date(v[0].dt * 1000), v.flatMap { it.weather }))
                    }

                    val format = SimpleDateFormat("dd.MM EEEE")
                    dateMapList.forEach {
                        val weatherUI = WeatherUI(format.format(it.date), getWeatherImageAndDescription(it.list).second)
                        weatherList.add(weatherUI)
                    }
                    adapter.setData(weatherList)
                }
            } catch (e: Exception) {
                Log.e("TAG", e.localizedMessage, e)
            }
        }
    }

    enum class IMAGE {
        RAIN, SNOW, SUN, CLOUDS
    }

    //https://openweathermap.org/weather-conditions
    private fun getWeatherImageAndDescription(weather: List<Weather>): Pair<String, IMAGE> {
        var imageR = Pair(weather[0].description, IMAGE.CLOUDS)
        var hasRain = false
        var hasClouds = false
        weather.forEach {
            val iconCode = it.icon.dropLast(1)
            if (iconCode == "13") {
                return Pair(it.description, IMAGE.SNOW)
            }

            if (iconCode in listOf<String>("09", "10", "11")) {
                imageR = Pair(it.description, IMAGE.RAIN)
                hasRain = true
            }

            if (iconCode in listOf<String>("02", "03", "04", "50") && !hasRain) {
                imageR = Pair(it.description, IMAGE.CLOUDS)
                hasClouds = true
            }

            if (iconCode == "01" && !hasRain && !hasClouds) {
                imageR = Pair(it.description, IMAGE.SUN)
            }
        }
        return imageR
    }

    private fun imageToDrawble(image: IMAGE) = when (image) {
        IMAGE.RAIN -> R.drawable.rain
        IMAGE.SNOW -> R.drawable.snow
        IMAGE.SUN -> R.drawable.sun
        IMAGE.CLOUDS -> R.drawable.clouds
    }

    data class DateWeatherMap(val date: Date, val list: List<Weather>)
}