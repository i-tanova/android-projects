package com.tanovait.sunnyapp.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.tanovait.sunnyapp.R
import com.tanovait.sunnyapp.api.APIClient
import com.tanovait.sunnyapp.api.APIInterface
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class MainActivity2 : AppCompatActivity() {
    val coroutineScope = CoroutineScope(Dispatchers.Main)
    val api = APIClient.client?.create(APIInterface::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)


        coroutineScope.launch(Dispatchers.IO) {
            try {
                val weatherResponse = api?.getWeather("Sofia", "metric")
                val forecastResponse = api?.getForecast("Sofia", "metric")

                withContext(Dispatchers.Main) {
                    weather_api_text.text = weatherResponse!!.weather[0].description
                    val sb = StringBuffer()
                    val format = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                    forecastResponse!!.list.forEach{
                        sb.append(format.format(Date(it.dt * 1000))+ " " + it.weather[0].description)
                        sb.appendLine()
                    }
                    forecast_api_text.text = sb.toString()
                }
            }catch (e: Exception){
                Log.e("TAG", e.localizedMessage, e)
            }
        }
    }
}