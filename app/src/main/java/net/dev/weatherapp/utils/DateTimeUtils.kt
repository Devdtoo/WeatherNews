package net.dev.weatherapp.utils

import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtils {
    fun getCurrentDate() : String{
        var calendar = Calendar.getInstance()
        val dayName = getDay(calendar.get(Calendar.DAY_OF_WEEK))


        val dateFormat = SimpleDateFormat("dd-MM")
        val formattedDate: String = dateFormat.format(System.currentTimeMillis())
        return  "$dayName $formattedDate"

    }

    private fun getDay(day : Int): String? {
        val days = mutableMapOf<Int,String>().apply {
            put(1,"Sun")
            put(2,"Mon")
            put(3,"Tues")
            put(4,"Wed")
            put(5,"Thr")
            put(6,"Fri")
            put(7,"Sat")
        }

        return days[day]

    }
}