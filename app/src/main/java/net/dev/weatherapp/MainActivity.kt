package net.dev.weatherapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import net.dev.weatherapp.fragments.HomeFragment
import net.dev.weatherapp.fragments.NewsFragment
import net.dev.weatherapp.fragments.SettingFragment
import timber.log.Timber


class MainActivity : AppCompatActivity() , TabLayout.OnTabSelectedListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tabLayout.addOnTabSelectedListener(this)
        showFragment(HomeFragment())
        Timber.plant(Timber.DebugTree())

    }



    override fun onTabReselected(p0: TabLayout.Tab?) {
        Timber.d("onTabReselected ${p0?.position}")

    }


    private fun showFragment(fragment : Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,fragment).commit()
    }

    override fun onTabUnselected(p0: TabLayout.Tab?) {
        Timber.d("onTabUnselected ${p0?.position}")
        when(p0?.position){
            0 -> {
                p0.icon =  getDrawable(R.drawable.ic_cloudicon_light)
            }
            1-> {
                p0.icon =  getDrawable(R.drawable.ic_newsicon)

            }
            2-> {
                p0.icon =  getDrawable(R.drawable.ic_settingicon)

            }
        }
    }

    override fun onTabSelected(p0: TabLayout.Tab?) {
        Timber.d("onTabSelected ${p0?.position}")
        when(p0?.position){
            0 -> {
                showFragment(HomeFragment())
                p0.icon =  getDrawable(R.drawable.ic_cloudicon)
            }
            1-> {
                showFragment(NewsFragment())
                p0.icon =  getDrawable(R.drawable.ic_newsicon_dark)

            }
            2-> {
                showFragment(SettingFragment())
                p0.icon =  getDrawable(R.drawable.ic_settingicon_dark)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }







}
