package com.tanovait.sunnyapp.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.tanovait.sunnyapp.R
import com.tanovait.sunnyapp.api.APIClient
import com.tanovait.sunnyapp.api.APIInterface
import com.tanovait.sunnyapp.data.WeatherResponse
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class MainActivity2 : AppCompatActivity() {
    val coroutineScope = CoroutineScope(Dispatchers.Main)
    val api = APIClient.client?.create(APIInterface::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        city_text.text = "Sofia"


        coroutineScope.launch(Dispatchers.IO) {
            try {
                val weatherResponse = api?.getWeather("Sofia", "metric")
                val forecastResponse = api?.getForecast("Sofia", "metric")

                withContext(Dispatchers.Main) {
                    val imageR = getWeatherImageR(weatherResponse)
                    today_image.setImageDrawable(getDrawable(imageR))
                    today_description.text = weatherResponse!!.weather[0].description
                    today_temperature.text = "${weatherResponse!!.main.temp} C"

//                    val sb = StringBuffer()
//                    val format = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
//                    forecastResponse!!.list.forEach{
//                        sb.append(format.format(Date(it.dt * 1000)) + " " + it.weather[0].description)
//                        sb.appendLine()
//                    }
//                    forecast_api_text.text = sb.toString()
                }
            }catch (e: Exception){
                Log.e("TAG", e.localizedMessage, e)
            }
        }
    }

    //https://openweathermap.org/weather-conditions
    private fun getWeatherImageR(weatherResponse: WeatherResponse?): Int {
        var imageR = R.drawable.sun
        weatherResponse!!.weather.forEach {
            val iconCode = it.icon.dropLast(1)
            if (iconCode in listOf<String>("13")) {
                return R.drawable.snow
            }

            if (iconCode in listOf<String>("09", "10", "11")) {
                imageR = R.drawable.rain
            }
        }
        return imageR
    }
}