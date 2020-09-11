package net.dev.weatherapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import net.dev.weatherapp.adapter.NewsAdapter
import net.dev.weatherapp.adapter.WeatherForecastAdapter
import net.dev.weatherapp.repo.NewsRepository
import net.dev.weatherapp.repo.WeatherRepository
import net.dev.weatherapp.repo.local.model.Forcast
import net.dev.weatherapp.repo.local.model.News
import net.dev.weatherapp.repo.local.model.Source
import net.dev.weatherapp.repo.local.model.Weather

class HomeFragmentViewModel(
    private val weatherRepository: WeatherRepository,
    private val newsRepository: NewsRepository
) : ViewModel() {

    var onApiRefreshSuccess = MutableLiveData<Boolean>()

    var onDataLoaded = MutableLiveData<Boolean>()


    //TODO: Default and secondry constuctor discussion
    init {
        requestNews()
        requestWeather()
    }


    fun requestNews() {
        viewModelScope.launch {
            newsRepository.requestNews {

            }
        }

    }

    fun requestWeather() {
//        val location = Location("")
//        location.latitude =26.8467
//        location.longitude = 80.9462
        viewModelScope.launch {
            weatherRepository.requestForecasts()

        }

    }

    fun setWeatherForecastAdapter(recyclerView: RecyclerView) {
        var forecastAdapter = WeatherForecastAdapter(fakeForcastItems())
        recyclerView.adapter = forecastAdapter
        weatherRepository.getForecasts {
            forecastAdapter = WeatherForecastAdapter(it)
            recyclerView.adapter = forecastAdapter
            forecastAdapter.notifyDataSetChanged()
        }
    }


    private fun fakeNewsItems(): MutableList<News> {
        val list = mutableListOf<News>().apply {
            add(News("", "", "", "", Source("", ""), "", "", null, false))
            add(News("", "", "", "", Source("", ""), "", "", null, false))
            add(News("", "", "", "", Source("", ""), "", "", null, false))
            add(News("", "", "", "", Source("", ""), "", "", null, false))
            add(News("", "", "", "", Source("", ""), "", "", null, false))
            add(News("", "", "", "", Source("", ""), "", "", null, false))
        }
        return list
    }

    private fun fakeForcastItems(): MutableList<Forcast> {
        val weather = mutableListOf<Weather>().apply {
            add(Weather("", "", 1, ""))
        }
        val list = mutableListOf<Forcast>().apply {
            add(
                Forcast(
                    0,
                    0.0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    Forcast.Temp(1.0, 1.0, 0.0, 1.0, 1.0, 1.0),
                    1.0,
                    weather,
                    0,
                    1.0
                )
            )
            add(
                Forcast(
                    0,
                    0.0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    Forcast.Temp(1.0, 1.0, 0.0, 1.0, 1.0, 1.0),
                    1.0,
                    weather,
                    0,
                    1.0
                )
            )
            add(
                Forcast(
                    0,
                    0.0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    Forcast.Temp(1.0, 1.0, 0.0, 1.0, 1.0, 1.0),
                    1.0,
                    weather,
                    0,
                    1.0
                )
            )
            add(
                Forcast(
                    0,
                    0.0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    Forcast.Temp(1.0, 1.0, 0.0, 1.0, 1.0, 1.0),
                    1.0,
                    weather,
                    0,
                    1.0
                )
            )
            add(
                Forcast(
                    0,
                    0.0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    Forcast.Temp(1.0, 1.0, 0.0, 1.0, 1.0, 1.0),
                    1.0,
                    weather,
                    0,
                    1.0
                )
            )
            add(
                Forcast(
                    0,
                    0.0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    Forcast.Temp(1.0, 1.0, 0.0, 1.0, 1.0, 1.0),
                    1.0,
                    weather,
                    0,
                    1.0
                )
            )
            add(
                Forcast(
                    0,
                    0.0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    Forcast.Temp(1.0, 1.0, 0.0, 1.0, 1.0, 1.0),
                    1.0,
                    weather,
                    0,
                    1.0
                )
            )

        }
        return list
    }

    //TODO: Discuss the background threading

    fun setNewsAdapter(recyclerView: RecyclerView) {
        var newsAdapter = NewsAdapter(fakeNewsItems())

        recyclerView.adapter = newsAdapter

        newsRepository.getTopHeadlines {

            newsAdapter = NewsAdapter(it)
            recyclerView.adapter = newsAdapter
            newsAdapter.notifyDataSetChanged()
            onDataLoaded.postValue(true)
            //TODO: It should update different observable which can show error message
            onApiRefreshSuccess.postValue(true)
        }
    }

    fun refreshData() {
//        val location = Location("")
//        location.latitude =26.8467
//        location.longitude = 80.9462
        viewModelScope.launch {
            weatherRepository.requestForecasts()
        }
        viewModelScope.launch {
            newsRepository.requestNews {
                onApiRefreshSuccess.postValue(it)
            }
        }

    }


//
//    fun onStop(){
//        cle
//        newsRepository.onDestroy()
//    }


}
