package net.dev.weatherapp.fragments

import android.os.Bundle
import net.dev.weatherapp.R
import net.dev.weatherapp.base.BaseFragment
import net.dev.weatherapp.databinding.WeatherFragmentBinding
import net.dev.weatherapp.repo.local.model.Forcast
import net.dev.weatherapp.viewmodels.MyViewModelFactory
import net.dev.weatherapp.viewmodels.WeatherFragmentViewModel


class WeatherFragment : BaseFragment<WeatherFragmentBinding, WeatherFragmentViewModel>() {

    companion object {
        fun newInstance() =
            WeatherFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.vm = viewModel

    }

    override fun getLayoutId() = R.layout.weather_fragment

    override fun getViewModelClass() = WeatherFragmentViewModel::class.java

    override fun getMyViewModelFactory() = MyViewModelFactory(WeatherFragmentViewModel::class){
        WeatherFragmentViewModel(arguments?.getParcelable<Forcast>("DATA_FORECAST"))
    }

}
