package net.dev.weatherapp.repo

import android.content.Context
import android.location.Location
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.dev.weatherapp.LocationManager
import net.dev.weatherapp.callbacks.LocationListeners
import net.dev.weatherapp.repo.local.WeatherAppDatabase
import net.dev.weatherapp.repo.local.model.Forcast
import net.dev.weatherapp.repo.local.model.WeatherResponse
import net.dev.weatherapp.repo.remote.Networking
import net.dev.weatherapp.repo.remote.Networking.WEATHER_BASE_URL
import timber.log.Timber
import java.io.File

class WeatherRepository(private val context : Context, private val owner : LifecycleOwner) :
    LocationListeners {
    private lateinit var locationManager : _root_ide_package_.net.dev.weatherapp.LocationManager
    private var disposables : CompositeDisposable
    private var db : WeatherAppDatabase

    private var lastLocation : Location = Location("")

    init {
        lastLocation.latitude = 26.8467
        lastLocation.longitude = 80.9462
        locationManager = LocationManager(context)
        locationManager.addListener(this)
        disposables = CompositeDisposable()
        db = WeatherAppDatabase.getInstance(context)
    }

    //TODO: Add caching mechanism for API
    suspend fun requestForecasts() {
      /*
      Handling API calls with RxJava
      disposables.add(  Networking.create(WEATHER_BASE_URL, File(""),5 * 1024 * 1024)
            .queryWeeklyForcast(lastLocation.latitude, lastLocation.longitude)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({
                val forecastDao = db.forecastDao()
                it.daily.forEach{forcast->
                    forecastDao.insertForecast(forcast)
                }
                Timber.d("successful fetch of Forecast Weather")

            },{
                Timber.e("$it")
            })
      )
       */
       withContext(Dispatchers.IO){
           val response = Networking.create(WEATHER_BASE_URL, File(""),5 * 1024 * 1024)
               .queryWeeklyForcast(lastLocation.latitude, lastLocation.longitude).body()
           response?.let{
               it.daily.forEach{forcast->
                   db.forecastDao().insertForecast(forcast)
               }
           }
       }
    }

    //return ROOM Query
    fun getForecasts(onSuccess : (List<Forcast>)-> Unit) {

       db.forecastDao().getWeeklyForecast().observe(owner, Observer {
           if(it.isNotEmpty()){
               onSuccess(it)
           }
       })
    }



    suspend fun requestCurrentWeather(location: Location) {
        /*
        Handling APIs with RxJava
        disposables.add(
            Networking.create(WEATHER_BASE_URL, File(""), 5 * 1024 * 1024)
                .queryCurrentWeather(location.latitude,location.longitude)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe({
                    db.currentWeatherDao().insertCurrentWeather(it)
                    Timber.d("successful fetch of Current Weather")
                },{
                    Timber.e("$it")
                })
        )
         */

        withContext(Dispatchers.IO){
           val response =  Networking.create(WEATHER_BASE_URL, File(""), 5 * 1024 * 1024)
                .queryCurrentWeather(location.latitude,location.longitude).body()

            response?.let {
                db.currentWeatherDao().insertCurrentWeather(it)
                Timber.d("successful fetch of Current Weather")
            }
        }
    }



    //return ROOM Query
    fun getCurrentWeather(onSuccess: (WeatherResponse) -> Unit){
        db.currentWeatherDao().getCurrentWeather().observe(owner, Observer{
            onSuccess(it)
        })
    }

    override fun onLastLocationFound(location: Location) {
        this.lastLocation = location

        //requestForecasts()
//        requestCurrentWeather()
    }

    fun onDestroy(){
        disposables.clear()
    }
}