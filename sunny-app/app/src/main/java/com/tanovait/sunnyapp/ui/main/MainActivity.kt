package com.tanovait.sunnyapp.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tanovait.sunnyapp.R
import com.tanovait.sunnyapp.ui.WeatherUI
import com.tanovait.sunnyapp.ui.detail.DetailFragment

// Images are taken from :https://pixabay.com/vectors/clouds-sunny-warm-patches-weather-37009/
const val EXTRA_DATE: String = "date"

class MainActivity2 : AppCompatActivity() {
    private val tag = "MainActivity2"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_screen_host)

        if (savedInstanceState == null) {
            val fragment = MainScreenFragment()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.main_content, fragment)
                .commit()
        }
    }

    fun show(item: WeatherUI) {
        val fragment = DetailFragment()
        val args = Bundle().apply {
            this.putString(EXTRA_DATE, item.daytime.toString())
        }
        fragment.arguments = args
        supportFragmentManager.beginTransaction()
            .addToBackStack(DetailFragment.TAG)
            .replace(R.id.main_content, fragment, DetailFragment.TAG)
            .commit()

        //Before using activity
//        Log.d(tag, "Day " + item.daytime)
//        val intent = Intent(this@MainActivity2, DetailActivity::class.java).apply{
//            this.putExtra(EXTRA_DATE, item.daytime.toString())
//        }
//        startActivity(intent)
    }
}