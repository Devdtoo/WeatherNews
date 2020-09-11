package net.dev.weatherapp.fragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_location_permission.view.*
import net.dev.weatherapp.MainActivity
import net.dev.weatherapp.R


class LocationPermissionFragment : Fragment() {
    companion object{
        const val LOCATION_PERMISSION_RC = 1
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View  = inflater.inflate(R.layout.fragment_location_permission, container, false)
        view.allowButton.setOnClickListener { askLocationPermission() }
        return view
    }

    fun askLocationPermission(){
        if(checkSelfPermission(context!!,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),LOCATION_PERMISSION_RC)
        }else{
            //move to another fragment
            moveToMainActivity()
        }
    }

    fun moveToMainActivity(){
        startActivity(Intent(context, MainActivity::class.java))
        activity?.finish()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == LOCATION_PERMISSION_RC && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            moveToMainActivity()
        }else{
            Toast.makeText(context,resources.getString(R.string.location_permission_not_given),Toast.LENGTH_LONG).show()
        }
    }


}