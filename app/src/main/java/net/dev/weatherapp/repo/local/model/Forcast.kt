package net.dev.weatherapp.repo.local.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import kotlin.math.roundToInt

@Parcelize
@Entity
data class Forcast(
    var clouds: Int,
    var dew_point: Double,

    @PrimaryKey
    var dt: Int,

//    @Ignore
//    var feels_like: FeelsLike,
    var humidity: Int,
    var pressure: Int,
    var sunrise: Int,
    var sunset: Int,

    @Embedded
    var temp: Temp,

    var uvi: Double,


    var weather: List<Weather>,
    var wind_deg: Int,
    var wind_speed: Double
) : Parcelable{
    data class FeelsLike(
        var day: Double,
        var eve: Double,
        var morn: Double,
        var night: Double
    )
    @Parcelize
    data class Temp(
        var day: Double,
        var eve: Double,
        var max: Double,
        var min: Double,
        var morn: Double,
        var night: Double
    ): Parcelable{
        fun getTempInCelcius(): String{
            if(max == 0.0) return ""
            return (max - ("273.15").toFloat()).roundToInt().toString() + "°C"
        }
    }

    fun getHumidityInString(): String{
        return humidity.toString() + "%"
    }

    fun getWindSpeedInString(): String{
        return (wind_speed.toFloat()).roundToInt().toString() + "Km/h"
    }
    
}