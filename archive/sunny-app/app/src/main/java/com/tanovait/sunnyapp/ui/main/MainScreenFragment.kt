package com.tanovait.sunnyapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import com.example.firstfirestore.MyAdapter
import com.example.firstfirestore.MyViewHolder
import com.tanovait.sunnyapp.R
import com.tanovait.sunnyapp.data.ForecastResponse
import com.tanovait.sunnyapp.data.Weather
import com.tanovait.sunnyapp.data.WeatherResponse
import com.tanovait.sunnyapp.ui.IMAGE
import com.tanovait.sunnyapp.ui.WeatherUI
import kotlinx.android.synthetic.main.main_screen.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

class MainScreenFragment: Fragment() {


    private lateinit var viewModel: WeatherViewModel
    private lateinit var viewModelFactory: WeatherViewModelFactory

    val adapter = object : MyAdapter<WeatherUI>() {

        override fun bind(t: WeatherUI, holder: MyViewHolder?) {
            val dayTxtV = holder?.itemView?.findViewById<TextView>(R.id.day_name)
            dayTxtV?.text = t.day

            val dayImageV = holder?.itemView?.findViewById<ImageView>(R.id.day_image)
            dayImageV?.setImageDrawable(getDrawable(context!!, imageToDrawble(t.icon)))
        }

        override fun getItemViewType(position: Int): Int {
            return R.layout.forecast_list_item
        }

    }

    // View initialization logic
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_screen, container, false)
    }

    // Post view initialization logic
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModelFactory = WeatherViewModelFactory(WeatherRepository(WeatherDataSourceImpl()))
        // keep this for historical view how to create ViewModel without arguments
        /// viewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(WeatherViewModel::class.java)

        //TODO move this in ViewModel
        city_text.text = "Amsterdam"
        weather_forecast_rv.adapter = adapter

        adapter.setOnItemClickListener(object: MyAdapter.OnItemClickListener<WeatherUI>{
            override fun onItemClick(item: WeatherUI) {
                if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                    (requireActivity() as MainActivity2).show(item)
                }
            }
        })

        viewModel.weatherLiveData.observe(viewLifecycleOwner) {
            weatherResponse(it)
        }

        viewModel.forecastLiveData.observe(viewLifecycleOwner) {
            forecastResponse(it)
        }
    }

    private fun weatherResponse(it: WeatherResponse?) {
        val (description, image) = getWeatherImageAndDescription(it!!.weather)
        today_image.setImageDrawable(getDrawable(requireContext(), imageToDrawble(image)))
        today_description.text = description
        today_temperature.text = "${it!!.main.temp} C"
    }

    private fun forecastResponse(forecastResponse: ForecastResponse?){
        val weatherList = mutableListOf<WeatherUI>()
        val dateMapList = mutableListOf<DateWeatherMap>()
        val dayGroup = forecastResponse!!.list.groupBy { it.dt_txt.split(Pattern.compile("\\s+"))[0] }
        for ((k, v) in dayGroup) {
            dateMapList.add(DateWeatherMap(Date(v[0].dt * 1000), v.flatMap { it.weather }))
        }

        val format = SimpleDateFormat("dd.MM EEEE")
        dateMapList.forEach {
            val weather =  getWeatherImageAndDescription(it.list)
            val weatherUI = WeatherUI(it.date.time, format.format(it.date), weather.first, weather.second)
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