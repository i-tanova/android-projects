package com.tanovait.sunnyapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.tanovait.sunnyapp.R
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private lateinit var viewModel: WeatherDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val daytime = intent.getStringExtra(EXTRA_DATE)
        dateTime.text = daytime.toString()

        viewModel = ViewModelProvider(this).get(WeatherDetailViewModel::class.java)
        viewModel.fetch(daytime!!)

        viewModel.forecastLiveData.observe(this, {
            dateTime.text = it.toString()
        })
    }
}