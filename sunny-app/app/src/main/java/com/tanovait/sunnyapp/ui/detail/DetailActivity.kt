package com.tanovait.sunnyapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.firstfirestore.MyAdapter
import com.example.firstfirestore.MyViewHolder
import com.tanovait.sunnyapp.R
import com.tanovait.sunnyapp.ui.IMAGE
import com.tanovait.sunnyapp.ui.WeatherUI
import com.tanovait.sunnyapp.ui.main.EXTRA_DATE
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private lateinit var viewModel: WeatherDetailViewModel

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
        setContentView(R.layout.activity_detail)

        weather_forecast_rv.adapter = adapter

        val daytime = intent.getStringExtra(EXTRA_DATE)
        viewModel = ViewModelProvider(this).get(WeatherDetailViewModel::class.java)
        viewModel.fetch(daytime!!)

        viewModel.forecastLiveData.observe(this, {
            adapter.setData(it.weatherByDays)
        })
    }
}

private fun imageToDrawble(image: IMAGE) = when (image) {
    IMAGE.RAIN -> R.drawable.rain
    IMAGE.SNOW -> R.drawable.snow
    IMAGE.SUN -> R.drawable.sun
    IMAGE.CLOUDS -> R.drawable.clouds
}