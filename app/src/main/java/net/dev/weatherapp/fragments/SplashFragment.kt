package net.dev.weatherapp.fragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import net.dev.weatherapp.MainActivity
import net.dev.weatherapp.R


class SplashFragment : Fragment() {


    private val SPLASH_TIME_OUT = 5000L


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_splash, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity

            checkLocationPermission()

        }, SPLASH_TIME_OUT)

    }

    fun checkLocationPermission(){
        if(ContextCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            moveToLocationPermissionFragment()

        }else{
            //move to another fragment
            moveToMainActivity()
        }
    }

    fun moveToLocationPermissionFragment() {
        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragmentContainer, LocationPermissionFragment())?.commit()
    }

    fun moveToMainActivity(){
        context?.startActivity(Intent(activity, MainActivity::class.java))
        activity?.finish()
    }



}