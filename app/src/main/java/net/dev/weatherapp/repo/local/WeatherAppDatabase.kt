package net.dev.weatherapp.repo.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import net.dev.weatherapp.repo.local.dao.ForecastDao
import net.dev.weatherapp.repo.local.dao.NewsDao
import net.dev.weatherapp.repo.local.dao.WeatherResponseDao
import net.dev.weatherapp.repo.local.model.Forcast
import net.dev.weatherapp.repo.local.model.News
import net.dev.weatherapp.repo.local.model.WeatherResponse
import net.dev.weatherapp.repo.local.typeconverters.ListTypeConverter

@Database(entities  = [News::class, Forcast::class, WeatherResponse::class], version = 1)
@TypeConverters(ListTypeConverter::class)
abstract class WeatherAppDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao
    abstract fun forecastDao() : ForecastDao
    abstract fun currentWeatherDao() : WeatherResponseDao

    companion object{
        const val DB_NAME  = "weatherhunt.db"

        @Volatile
        private var INSTANCE : WeatherAppDatabase? = null

        fun getInstance(context : Context) : WeatherAppDatabase{
            if(INSTANCE == null){
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        WeatherAppDatabase::class.java,
                        DB_NAME
                    ).build()
                    return INSTANCE!!
                }
            }
            return INSTANCE!!
        }

    }
}