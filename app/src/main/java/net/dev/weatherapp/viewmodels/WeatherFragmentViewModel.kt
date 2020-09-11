package net.dev.weatherapp.viewmodels

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import net.dev.weatherapp.repo.local.model.Forcast

class WeatherFragmentViewModel(private val forecast: Forcast?) : ViewModel() {

    var weatherOfTheDay = ObservableField<Forcast>()
    lateinit var weather: Forcast

    init {
        weatherOfTheDay.set(forecast)
        weather = forecast!!
    }



}



