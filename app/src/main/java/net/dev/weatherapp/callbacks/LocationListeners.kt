package net.dev.weatherapp.callbacks

import android.location.Location

interface LocationListeners {
     fun onLastLocationFound(location : Location)
}