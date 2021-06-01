package com.tanovait.sunnyapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.firstfirestore.MyAdapter
import com.example.firstfirestore.MyViewHolder
import com.tanovait.sunnyapp.R
import com.tanovait.sunnyapp.data.ForecastResponse
import com.tanovait.sunnyapp.data.Weather
import com.tanovait.sunnyapp.data.WeatherResponse
import com.tanovait.sunnyapp.data.WeatherUI
import kotlinx.android.synthetic.main.activity_main2.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

// Images are taken from :https://pixabay.com/vectors/clouds-sunny-warm-patches-weather-37009/
const val EXTRA_DATE: String = "date"
class MainActivity2 : AppCompatActivity() {
    private val tag = "MainActivity2"

    private lateinit var viewModel: WeatherViewModel

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

        viewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)

        city_text.text = "Sofia"
        weather_forecast_rv.adapter = adapter

        adapter.setOnItemClickListener(object: MyAdapter.OnItemClickListener<WeatherUI>{
            override fun onItemClick(item: WeatherUI) {
                Log.d(tag, "Day " + item.daytime)
                val intent = Intent(this@MainActivity2, DetailActivity::class.java).apply {
                    putExtra(EXTRA_DATE, item.daytime)
                }
                startActivity(intent)
            }
        })

        viewModel.weatherLiveData.observe(this, {
            weatherResponse(it)
        })

        viewModel.forecastLiveData.observe(this, {
            forecastResponse(it)
        })
    }

    private fun weatherResponse(it: WeatherResponse?) {
        val (description, image) = getWeatherImageAndDescription(it!!.weather)
        today_image.setImageDrawable(getDrawable(imageToDrawble(image)))
        today_description.text = description
        today_temperature.text = "${it!!.main.temp} C"
    }

    enum class IMAGE {
        RAIN, SNOW, SUN, CLOUDS
    }

    private fun forecastResponse(forecastResponse: ForecastResponse?){
        val weatherList = mutableListOf<WeatherUI>()
        val dateMapList = mutableListOf<MainActivity2.DateWeatherMap>()
        val dayGroup = forecastResponse!!.list.groupBy { it.dt_txt.split(Pattern.compile("\\s+"))[0] }
        for ((k, v) in dayGroup) {
            dateMapList.add(MainActivity2.DateWeatherMap(Date(v[0].dt * 1000), v.flatMap { it.weather }))
        }

        val format = SimpleDateFormat("dd.MM EEEE")
        dateMapList.forEach {
            val weatherUI = WeatherUI(it.date.time, format.format(it.date), getWeatherImageAndDescription(it.list).second)
            weatherList.add(weatherUI)
        }
        adapter.setData(weatherList)
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