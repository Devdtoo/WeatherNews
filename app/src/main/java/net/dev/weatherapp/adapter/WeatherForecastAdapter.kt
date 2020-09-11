package net.dev.weatherapp.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.weather_item.view.*
import net.dev.weatherapp.base.BaseRecyclerViewAdapter
import net.dev.weatherapp.base.BaseViewHolder
import net.dev.weatherapp.databinding.WeatherItemBinding
import net.dev.weatherapp.repo.local.model.Forcast
import net.dev.weatherapp.R
import net.dev.weatherapp.fragments.WeatherFragment


class WeatherForecastAdapter(val forecasts: List<Forcast>) :
    BaseRecyclerViewAdapter<Forcast, WeatherItemBinding>() {


    init {
        addItems(forecasts)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<WeatherItemBinding>, position: Int) {
        onBind(holder.binding, itemList[position])
    }

    private fun onBind(binding: WeatherItemBinding, forcast: Forcast) {
        binding.weatherResponse = forcast
        binding.root.card_view.setOnClickListener {
            showWeatherFragment(binding, forcast)
        }

    }

    private fun showWeatherFragment(binding: WeatherItemBinding, forecast: Forcast) {
        val activity = binding.root.context as AppCompatActivity
        val weatherFragment = WeatherFragment()
        //add Object Data to fragment
        val args = Bundle()
        args.putParcelable("DATA_FORECAST", forecast)
        weatherFragment.arguments = args

        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, weatherFragment).addToBackStack("backstack").commit()

    }

    override fun getLayoutId(): Int = R.layout.weather_item


}