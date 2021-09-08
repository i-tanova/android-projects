package com.tanovait.sunnyapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.firstfirestore.MyAdapter
import com.example.firstfirestore.MyViewHolder
import com.tanovait.sunnyapp.R
import com.tanovait.sunnyapp.ui.IMAGE
import com.tanovait.sunnyapp.ui.WeatherUI
import com.tanovait.sunnyapp.ui.main.EXTRA_DATE
import kotlinx.android.synthetic.main.detail_screen.*

class DetailFragment: Fragment() {

    private lateinit var viewModel: WeatherDetailViewModel
    private lateinit var viewModelFactory: DetailViewModelFactory

    private val adapter = object : MyAdapter<WeatherUI>() {
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
        return inflater.inflate(R.layout.detail_screen, container, false)
    }

    // Post view initialization logic
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        weather_forecast_rv.adapter = adapter
        val daytime = arguments?.getString(EXTRA_DATE)
        viewModelFactory = DetailViewModelFactory(DetailsRepository(DetailsDataSourceImpl()))
        //viewModel = ViewModelProvider(this).get(WeatherDetailViewModel::class.java)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(WeatherDetailViewModel::class.java)
        //TODO fetch every time, see how to reuse
        viewModel.fetch(daytime!!)

        viewModel.forecastLiveData.observe(this, {
            adapter.setData(it.weatherByDays)
        })
    }

    private fun imageToDrawble(image: IMAGE) = when (image) {
        IMAGE.RAIN -> R.drawable.rain
        IMAGE.SNOW -> R.drawable.snow
        IMAGE.SUN -> R.drawable.sun
        IMAGE.CLOUDS -> R.drawable.clouds
    }

    companion object{
        const val TAG = "DetailFragment"
    }
}