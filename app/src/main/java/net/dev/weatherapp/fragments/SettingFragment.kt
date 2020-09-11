package net.dev.weatherapp.fragments

import android.app.Activity
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.*
import com.google.android.libraries.places.api.model.Place
import com.rtchagas.pingplacepicker.PingPlacePicker
import kotlinx.android.synthetic.main.setting_fragment.*
import net.dev.weatherapp.NotificationWorker
import net.dev.weatherapp.StartupActivity.Companion.NOTIFICATION_WORK_TAG
import net.dev.weatherapp.base.BaseFragment
import net.dev.weatherapp.databinding.SettingFragmentBinding
import net.dev.weatherapp.repo.NewsRepository
import net.dev.weatherapp.viewmodels.MyViewModelFactory
import net.dev.weatherapp.viewmodels.SettingFragmentViewModel
import java.util.concurrent.TimeUnit


class SettingFragment : BaseFragment<SettingFragmentBinding, SettingFragmentViewModel>() {

    companion object {
        fun newInstance() =
            SettingFragment()
    }

    private lateinit var newsRepository : NewsRepository
    private val pingActivityRequestCode = 1001

    private val SETTING_SHARED_PREF = "SETTING_SHARED_PREF"
    private val SELECTED_ADDRESS = "SELECTED_ADDRESS"
    private val NOTIFICATION = "NOTIFICATION"


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        newsRepository = NewsRepository(viewLifecycleOwner,context!!)

            binding.vm = viewModel
        // TODO: Use the ViewModel

        binding.recyclerViewBookmark.layoutManager = LinearLayoutManager(context)
        viewModel.setAdapter(binding.recyclerViewBookmark)

        populateView()

        tv_change.setOnClickListener {
            showPlacePicker()
        }

        switchButton.isChecked = getNotificationStatus()
        switchButton.setOnCheckedChangeListener { _, isChecked ->
            setNotification(isChecked)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if ((requestCode == pingActivityRequestCode) && (resultCode == Activity.RESULT_OK)) {
            val place: Place? = PingPlacePicker.getPlace(data!!)

            tv_location_icon.text = place?.address
            storeSelectedAddress(place?.address)
            Toast.makeText(context, "You selected: ${place?.name}\n ${place?.id}", Toast.LENGTH_LONG).show()
        }
    }

    fun showPlacePicker(){
        val builder = PingPlacePicker.IntentBuilder()

        builder.setAndroidApiKey(getString(net.dev.weatherapp.R.string.key_google_apis_android))
            .setMapsApiKey(getString(net.dev.weatherapp.R.string.key_google_apis_maps))

        // If you want to set a initial location
        // rather then the current device location.
        // pingBuilder.setLatLng(LatLng(37.4219999, -122.0862462))

        try {
            val placeIntent = builder.build(activity as Activity)
            startActivityForResult(placeIntent, pingActivityRequestCode)
        } catch (ex: Exception) {
            Toast.makeText(context, "Google Play Services is not Available ${ex.message}", Toast.LENGTH_LONG).show()
        }
    }

    fun populateView(){
        tv_location_icon.text = getAddress() ?: "Unknown Address"
    }



    fun storeSelectedAddress(address : String?){
        val sharedPreference = activity?.getSharedPreferences(SETTING_SHARED_PREF,MODE_PRIVATE)
        sharedPreference?.edit()?.putString(SELECTED_ADDRESS,address)?.apply()
    }

    fun getAddress(): String?{
        val sharedPreference = activity?.getSharedPreferences(SETTING_SHARED_PREF,MODE_PRIVATE)
        return sharedPreference?.getString(SELECTED_ADDRESS,null)
    }

    fun setNotification(status : Boolean){
        val sharedPreference = activity?.getSharedPreferences(SETTING_SHARED_PREF,MODE_PRIVATE)
        sharedPreference?.edit()?.putBoolean(NOTIFICATION,status)?.apply()

        if(!status) disableWorkManager()
        else enableWorkManager()
    }

    fun disableWorkManager(){
        WorkManager.getInstance(context!!).cancelAllWorkByTag(NOTIFICATION_WORK_TAG)
    }

    fun enableWorkManager(){
        fun scheduleWork(tag: String?) {
            val notificationWork = PeriodicWorkRequest.Builder(
                NotificationWorker::class.java, 16, TimeUnit.MINUTES
            )
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val request = notificationWork
                .setConstraints(constraints).build()

            WorkManager.getInstance(context!!)
                .enqueueUniquePeriodicWork(tag!!, ExistingPeriodicWorkPolicy.KEEP, request)
        }
    }

    fun getNotificationStatus(): Boolean{
        val sharedPreference = activity?.getSharedPreferences(SETTING_SHARED_PREF,MODE_PRIVATE)
        return sharedPreference?.getBoolean(NOTIFICATION,false) ?: false
    }

    override fun getLayoutId() = net.dev.weatherapp.R.layout.setting_fragment

    override fun getViewModelClass() = SettingFragmentViewModel::class.java

    override fun getMyViewModelFactory() = MyViewModelFactory(SettingFragmentViewModel::class) {
        SettingFragmentViewModel(NewsRepository(viewLifecycleOwner,context!!))
    }


}
