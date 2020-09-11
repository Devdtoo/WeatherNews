package net.dev.weatherapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.work.*
import net.dev.weatherapp.fragments.SplashFragment
import java.util.concurrent.TimeUnit


class StartupActivity : AppCompatActivity() {

    companion object{
        val NOTIFICATION_WORK_TAG = "mywork"

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_startup)
        scheduleWork(NOTIFICATION_WORK_TAG)
        showFragment(SplashFragment())
    }


    fun scheduleWork(tag: String?) {
        val notificationWork = PeriodicWorkRequest.Builder(
            NotificationWorker::class.java, 16, TimeUnit.MINUTES
        )
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request = notificationWork
            .setConstraints(constraints).build()

        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork(tag!!, ExistingPeriodicWorkPolicy.KEEP, request)
    }

    private fun showFragment(fragment : Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,fragment).commit()
    }

    /*private fun loadFragment(fragment: Fragment) {
            // load fragment
            val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
    }*/
}