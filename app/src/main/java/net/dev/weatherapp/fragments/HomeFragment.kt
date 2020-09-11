package net.dev.weatherapp.fragments

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.home_fragment.*
import net.dev.weatherapp.R
import net.dev.weatherapp.base.BaseFragment
import net.dev.weatherapp.databinding.HomeFragmentBinding
import net.dev.weatherapp.repo.NewsRepository
import net.dev.weatherapp.repo.WeatherRepository
import net.dev.weatherapp.viewmodels.HomeFragmentViewModel
import net.dev.weatherapp.viewmodels.MyViewModelFactory

class HomeFragment : BaseFragment<HomeFragmentBinding, HomeFragmentViewModel>(){


    fun handleViews(){
        refreshLayoutHomeFragment.setOnRefreshListener {
            viewModel.refreshData()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.vm = viewModel

        rv_newsCards.layoutManager = LinearLayoutManager(context)
        viewModel.setNewsAdapter(rv_newsCards)


        val layoutManager = LinearLayoutManager(context,RecyclerView.HORIZONTAL,false)
        rv_weatherCards.layoutManager = layoutManager
        viewModel.setWeatherForecastAdapter(rv_weatherCards)
        viewModel.onApiRefreshSuccess.observe(viewLifecycleOwner, Observer {
           if(refreshLayoutHomeFragment.isRefreshing) refreshLayoutHomeFragment.isRefreshing = false

        })

        viewModel.onDataLoaded.observe(viewLifecycleOwner, Observer {
            if(it){
                shimmer_view_container.stopShimmer()
                shimmer_view_container.hideShimmer()
            }

        })
        handleViews()

    }
    override fun getLayoutId() =  R.layout.home_fragment

    override fun getViewModelClass() = HomeFragmentViewModel::class.java

    override fun getMyViewModelFactory() = MyViewModelFactory(HomeFragmentViewModel::class){
            HomeFragmentViewModel(
                WeatherRepository(context!!,viewLifecycleOwner),
                NewsRepository(viewLifecycleOwner,context!!)
            )

        }


}
