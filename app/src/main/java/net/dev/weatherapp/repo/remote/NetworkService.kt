package net.dev.weatherapp.repo.remote

import net.dev.weatherapp.repo.local.model.ForecastResponse
import net.dev.weatherapp.repo.local.model.NewsResponse
import net.dev.weatherapp.repo.local.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

//TODO: Refactor the API KEY place as well as the constants in thq query annotation
interface NetworkService {

    @GET(EndPoints.CURRENT_WEATHER_END_POINT)
    suspend fun queryCurrentWeather(
        @Query("lat") lat : Double,
        @Query("lon") lon : Double,
        @Query("appid") appId : String = EndPoints.WEATHER_API_KEY
    ): Response<WeatherResponse>

    @GET(EndPoints.FORCAST_ENDPOINT)
    suspend fun queryWeeklyForcast(
        @Query("lat") latitude : Double,
        @Query("lon") longitude : Double,
        @Query("exclude") excluded : String = "hourly,current",
        @Query("appid") appId : String = EndPoints.WEATHER_API_KEY
    ) : Response<ForecastResponse>

    @GET(EndPoints.NEWS_ENDPOINT)
    suspend fun queryTopHeadlines(
        @Query("country") country : String,
        @Query("category") category : String,
        @Query("apiKey") apiKey : String = EndPoints.NEWS_API_KEY
    ) : Response<NewsResponse>



    /*
    RxJava based retrofit implementation
    @GET(EndPoints.CURRENT_WEATHER_END_POINT)
    fun queryCurrentWeather(
        @Query("lat") lat : Double,
        @Query("lon") lon : Double,
        @Query("appid") appId : String = EndPoints.WEATHER_API_KEY
    ): Single<WeatherResponse>

    @GET(EndPoints.FORCAST_ENDPOINT)
    fun queryWeeklyForcast(
        @Query("lat") latitude : Double,
        @Query("lon") longitude : Double,
        @Query("exclude") excluded : String = "hourly,current",
        @Query("appid") appId : String = EndPoints.WEATHER_API_KEY
    ) : Single<ForecastResponse>

    @GET(EndPoints.NEWS_ENDPOINT)
    fun queryTopHeadlines(
        @Query("country") country : String,
        @Query("category") category : String,
        @Query("apiKey") apiKey : String = EndPoints.NEWS_API_KEY
    ) : Single<NewsResponse>


     */


}