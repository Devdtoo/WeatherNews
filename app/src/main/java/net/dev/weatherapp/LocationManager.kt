package net.dev.weatherapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import net.dev.weatherapp.callbacks.LocationListeners
import java.lang.ref.WeakReference

/*
    1. Fetch the current location here
    2. Pass the current location to listeners
 */

class LocationManager(private val context : Context) {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var listeners = mutableListOf<WeakReference<LocationListeners>>()
    init {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener {
                listeners.forEach { listener->
                    if(it != null){

                        listener.get()?.onLastLocationFound(it)

                    }
                }
            }
        }

    }

    fun addListener(listener : LocationListeners){
        listeners.add(WeakReference(listener))
    }

}